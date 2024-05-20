package site.patrickshao.mypan;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;
import site.patrickshao.mypan.test.VacuousVariableLockMap;
import site.patrickshao.mypan.utils.NonReentrantLockMap;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/8
 */
public class NonReentrantLockMapTest {
    NonReentrantLockMap<Integer> lock = new VacuousVariableLockMap<>();
    ConcurrentHashMap<Integer, Stopwatch> clockMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, Integer> useMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, Integer> readTolMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, Integer> writeTolMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, byte[]> fileMap = new ConcurrentHashMap<>();
    final int fileNum = 10;
    final int threadNum = 10;
    final int fileLength = 4 * 1024;
    final int sleepSeconds = 1;
    final AtomicInteger actWriteNum = new AtomicInteger(0);
    final AtomicInteger actCheckNum = new AtomicInteger(0);
    final AtomicInteger errorCount = new AtomicInteger(0);
    ExecutorService executorService = Executors.newFixedThreadPool(16 * 1024);

    @Test
    void mainTest() throws Exception {

        genFiles();
        writeFiles();
        checkFiles();
        assert errorCount.get() == 0;

        for (int i = 0; i < fileNum; i++) {
            clockMap.put(i, Stopwatch.createStarted());
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < threadNum; i++) {
            genWrite();
            genReads();
            int c = 0;
        }

        stopwatch.stop();
        System.out.println("GEN Time passed: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");


        for (int i = 0; i < sleepSeconds; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("====== Time passed: " + i + "s =======\n" +
                    "Read: " + readTolMap +
                    "\nWrite: " + writeTolMap +
                    "\nError: " + errorCount +
                    "\n====================================");
        }

        System.out.println("GEN Time use: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "s");
        System.out.println("Read (counts " + actCheckNum + "): " + readTolMap);
        System.out.println("Write: (counts " + actWriteNum + ")" + writeTolMap);
        System.out.println("Use: " + clockMap);
        System.out.println("Error: " + errorCount);
    }

    void genFiles() {
        for (int i = 0; i < fileNum; i++) {
            byte[] file = new byte[fileLength];
            for (int j = 0; j < fileLength; j++) {
                file[j] = (byte) (j % 127);
            }
            fileMap.put(i, file);
        }
    }

    void writeFiles() {
        for (int i = 0; i < fileNum; i++) {
            writeFile(i);
        }
    }

    void writeFile(int i) {
        var file = fileMap.get(i);
        for (int j = 0; j < fileLength; j++) {
            file[j] = file[j]++;
            file[j] = file[j]++;
        }
        for (int j = 0; j < fileLength; j++) {
            file[j] = file[j]--;
            file[j] = file[j]--;
        }
        actWriteNum.incrementAndGet();
    }

    void checkFiles() {
        for (int i = 0; i < fileNum; i++) {
            checkFile(i);
        }
    }

    void checkFile(int fileId) {
        var file = fileMap.get(fileId);
        for (int i = 0; i < fileLength; i++) {
            if (file[i] != (byte) (i % 127)) {
                errorCount.incrementAndGet();
            }
        }
        actCheckNum.incrementAndGet();
    }

    void genReads() {
        for (int i = 0; i < threadNum; i++) {
            for (int j = 0; j < fileNum; j++) {
                int finalJ = j;
                executorService.submit(() -> {
                    try {
                        read(finalJ, new Random().nextInt(100) + 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    void genWrite() {
        for (int i = 0; i < threadNum / 10; i++) {
            for (int j = 0; j < fileNum; j++) {
                int finalJ = j;
                executorService.submit(() -> {
                    try {
                        write(finalJ, new Random().nextInt(100) + 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    void read(int fileId, int times) throws Exception {
        readTolMap.put(fileId, Math.max(readTolMap.getOrDefault(fileId, 0), times));
        useMap.put(fileId, useMap.getOrDefault(fileId, 0) + 1);
        System.out.println("Read " + fileId + " start" + " at " +
                clockMap.get(fileId).elapsed(TimeUnit.MILLISECONDS) + "ms" + " by " +
                Thread.currentThread().getName() + " for " + times + "times");
        lock.lockRead(fileId);

        for (int i = 0; i < times; i++) {
            checkFile(fileId);
        }

        lock.unlockRead(fileId);
        lock.unlockRead(fileId);
        lock.unlockRead(fileId);
        useMap.put(fileId, useMap.getOrDefault(fileId, 0) - 1);
//        if (useMap.get(fileId) == 0) {
//            clockMap.put(fileId, clockMap.get(fileId).stop());
//        }
    }

    void write(int fileId, int times) throws Exception {
        writeTolMap.put(fileId, writeTolMap.getOrDefault(fileId, 0) + times);
        useMap.put(fileId, useMap.getOrDefault(fileId, 0) + 1);
        System.out.println("Write " + fileId + " start" + " at " +
                clockMap.get(fileId).elapsed(TimeUnit.MILLISECONDS) + "ms" + " by " +
                Thread.currentThread().getName() + " for " + times + "times");
        lock.lockWrite(fileId);

        for (int i = 0; i < times; i++) {
            writeFile(fileId);
        }
        lock.unlockWrite(fileId);
        lock.unlockWrite(fileId);
        lock.unlockWrite(fileId);
        useMap.put(fileId, useMap.getOrDefault(fileId, 0) - 1);
//        if (useMap.get(fileId) == 0) {
//            clockMap.put(fileId, clockMap.get(fileId).stop());
//        }
    }


}
