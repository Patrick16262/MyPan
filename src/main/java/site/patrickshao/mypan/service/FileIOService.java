package site.patrickshao.mypan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.patrickshao.mypan.entity.FilePO;
import site.patrickshao.mypan.repository.FileCacheRepository;

/**
 * 负责处理
 * 1.文件缓存和文件缓存表
 * 2.负责计算文件存储空间占用
 * 3.负责文件读写
 *
 * @author Shao Yibo
 * @description
 * @date 2024/5/19
 */
@Service
public class FileIOService {
    @Autowired
    private FileCacheRepository fileCacheRepository;

    /**
     * 准备读取文件，如果文件没有被缓存，则缓存一份
     * 可能会抛出
     * USER_OUT_OF_SPACE_444(444, "User Out Of Space"),
     * SERVER_OUT_OF_SPACE_445(445, "Server Out Of Space"),
     * 这两种异常
     *
     * @author Shao Yibo
     * @date 2024/5/19
     */

    public void openRead(FilePO file) {

    }

    /**
     * 准备写文件，缓存一份响应文件
     * 可能会抛出
     * USER_OUT_OF_SPACE_444(444, "User Out Of Space"),
     * SERVER_OUT_OF_SPACE_445(445, "Server Out Of Space"),
     * 这两种异常
     *
     * @author Shao Yibo
     * @date 2024/5/19
     */

    public void openWrite(FilePO file) {

    }

    public int read(int offset, int len, byte[] arr) {
        return 0;
    }

    public int write(int offset, int len, byte[] arr) {
        return 0;
    }

    public void closeRead(FilePO file) {

    }

    public void closeWrite(FilePO file) {

    }
}
