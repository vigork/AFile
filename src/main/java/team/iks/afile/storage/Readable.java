package team.iks.afile.storage;

import java.io.OutputStream;
import java.util.List;

/**
 * 文件读操作
 *
 * @author vigork
 * At: 2023/1/1
 */
public interface Readable {
    /**
     * 获取文件列表
     */
    List<FileItem> list(String virtualPath);

    /**
     * 读取文件
     */
    OutputStream read(String virtualPath);
}
