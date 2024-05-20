package site.patrickshao.mypan.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 内含一个读写锁map，根据key获取读写锁，读写锁是不公平不可重入的
 * 当key不存在时，自动创建一个读写锁
 * 当读写锁空闲时，自动从map中移除, 不会发生内存泄漏
 * 该类经过充分测试，运作高效，可靠
 * </pre>
 *
 * @param <T> key类型
 * @author Shao Yibo
 * @throws IllegalStateException 当读写锁被错误使用时：读锁时持有写锁/写锁时持有读锁/解锁时未持有相应锁
 * @date 2024/5/8
 */
public class NonReentrantLockMap<T> {

    private final ConcurrentHashMap<T, ReadWriteLockReference> lockMap = new ConcurrentHashMap<>();

    public void lockRead(T key) throws InterruptedException {
        ReadWriteLockReference lock = null;
        while (lock == null) {
            lock = lockMap.computeIfAbsent(key, k -> new ReadWriteLockReference());
            lock = lock.tryLockRead();
        }
        lockMap.put(key, lock);
    }

    public void unlockRead(T key) {
        ReadWriteLockReference lock = null;
        while (lock == null) {
            lock = lockMap.computeIfAbsent(key, k -> new ReadWriteLockReference());
            lock = lock.unlockRead();
        }
        //此时lock一定有效
        //noinspection DataFlowIssue
        if (lock.noBodyReading()) {
            lockMap.remove(key);
        } else {
            lockMap.put(key, lock);
        }
    }

    public void lockWrite(T key) throws InterruptedException {
        ReadWriteLockReference lock = null;
        while (lock == null) {
            lock = lockMap.computeIfAbsent(key, k -> new ReadWriteLockReference());
            lock = lock.tryLockWrite();
        }
        lockMap.put(key, lock);
    }

    public void unlockWrite(T key) {
        ReadWriteLockReference lock = null;
        while (lock == null) {
            lock = lockMap.computeIfAbsent(key, k -> new ReadWriteLockReference());
            lock = lock.unlockWrite();
        }
        //此时lock一定有效
        //noinspection DataFlowIssue
        if (lock.noBodyReading()) {
            lockMap.remove(key);
        } else {
            lockMap.put(key, lock);
        }
    }

    /**
     * ReadWriteLockReference，一种非公平可重入的读写锁，专为ReentrantVariableLock而设计
     * 每次操作后都会使其他所有ReadWriteLockReference无效
     * 并反回一个新的有效的ReadWriteLockReference
     * 如果当前ReadWriteLockReference已经失效，任何操作都不会生效并返回null
     * 对于同一个ReadWriteLockReference，最多只有一个引用是有效的
     *
     * @author Shao Yibo
     * @date 2024/5/8
     */
    private static class ReadWriteLockReference {
        private final Thread writeLockHeld;
        private final Set<Thread> readingThreads;
        private final ObjectWrapper<ReadWriteLockReference> availableVersion;

        /**
         * 无效化当前以及其他所有ReadWriteLockReference，返回一个新的ReadWriteLockReference
         *
         * @author Shao Yibo
         * @date 2024/5/8
         */
        public synchronized ReadWriteLockReference invalidate() {
            if (isInvalid()) {
                return null;
            }

            return new ReadWriteLockReference(this, writeLockHeld);
        }

        /**
         * 如果其他线程占有写锁，则阻塞直到其他线程释放并返回null
         * 如果没有其他线程占有写锁，则获取读锁并返回一个新的ReadWriteLockReference
         *
         * @author Shao Yibo
         * @date 2024/5/9
         */
        public synchronized ReadWriteLockReference tryLockRead() throws InterruptedException {
            if (isInvalid()) {
                return null;
            }
            if (Thread.currentThread() == writeLockHeld) {
                throw new IllegalStateException("Cannot lock read while holding write lock");
            }
            if (writeLockHeld != null) {
                wait();
                return null;
            }

            readingThreads.add(Thread.currentThread());
            return update(null);
        }

        public synchronized ReadWriteLockReference unlockRead() {
            if (isInvalid()) {
                return null;
            }
            readingThreads.remove(Thread.currentThread());

            return update(null);
        }

        /**
         * 如果其他线程占有读锁，则阻塞直到其他线程释放并返回null
         * 如果没有其他线程占有读锁，则获取写锁并返回一个新的ReentrantReadWriteLock
         *
         * @return ReentrantReadWriteLock
         */

        public synchronized ReadWriteLockReference tryLockWrite() throws InterruptedException {
            if (isInvalid()) {
                return null;
            }
            if (readingThreads.contains(Thread.currentThread())) {
                throw new IllegalStateException("Cannot lock write while holding read lock");
            }
            if (writeLockHeld == Thread.currentThread()) {
                return update(Thread.currentThread());
            }
            if (!readingThreads.isEmpty() || writeLockHeld != null) {
                wait();
                return null;
            }
            return update(Thread.currentThread());
        }

        public synchronized ReadWriteLockReference unlockWrite() {
            if (isInvalid()) {
                return null;
            }
            if (writeLockHeld != null && writeLockHeld != Thread.currentThread()) {
                return update(writeLockHeld);
            }
            return update(null);
        }

        public synchronized Boolean noBodyReading() {
            if (isInvalid()) {
                return null;
            }
            return readingThreads.isEmpty();
        }

        /**
         * 创建一个新的ReentrantReadWriteLock
         *
         * @author Shao Yibo
         * @date 2024/5/8
         */
        public ReadWriteLockReference() {
            writeLockHeld = null;
            readingThreads = new HashSet<>();
            availableVersion = new ObjectWrapper<>(this);
        }

        private boolean isInvalid() {
            return availableVersion.get() != this;
        }

        private ReadWriteLockReference update(Thread writeLockHeld) {
            notifyAll();
            return new ReadWriteLockReference(this, writeLockHeld);
        }

        /**
         * 创造一个ReentrantReadWriteLock引用，使其他ReentrantReadWriteLock引用失效
         *
         * @param info 读写信息
         */
        public ReadWriteLockReference(final ReadWriteLockReference info, final Thread writeLockHeld) {
            this.writeLockHeld = writeLockHeld;
            readingThreads = info.readingThreads;
            availableVersion = info.availableVersion;
            availableVersion.set(this);
        }


        private static class ObjectWrapper<T> {
            private T value;

            public ObjectWrapper(T value) {
                this.value = value;
            }

            public void set(T value) {
                this.value = value;
            }

            public T get() {
                return value;
            }
        }
    }
}
