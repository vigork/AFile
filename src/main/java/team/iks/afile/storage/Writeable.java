package team.iks.afile.storage;

import java.io.InputStream;

/**
 * 文件写操作
 *
 * @author vigork
 * At: 2023/1/1
 */
public interface Writeable {
    /**
     * 新建文件夹
     */
    FileItem makeDir(String virtualPath);

    /**
     * 写入文件
     */
    FileItem write(String virtualPath, InputStream is);

    /**
     * 删除文件
     */
    void delete(String virtualPath);

    /**
     * 移动文件
     */
    FileItem move(String virtualPath, String newVirtualPath);

    /**
     * 复制文件
     */
    FileItem copy(String virtualPath, String targetVirtualPath);
}
