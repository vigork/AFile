package team.iks.afile.driver;

import java.io.InputStream;

import team.iks.afile.pojo.vo.FileItemVO;

/**
 * 文件写操作
 *
 * @author vigork
 * At: 2023/1/1
 */
public interface Writeable {
    /**
     * 新建目录, 当目录地址对应的 File 已存在时抛出错误信息
     * @param virtualAbsolutePath 目录地址
     */
    FileItemVO makeDir(String virtualAbsolutePath);

    /**
     * 写入文件
     */
    FileItemVO write(String virtualAbsolutePath, InputStream in);

    /**
     * 删除文件
     */
    void delete(String virtualAbsolutePath);

    /**
     * 移动文件
     */
    FileItemVO move(String virtualAbsolutePath, String targetVirtualAbsolutePath);

    /**
     * 复制文件
     */
    FileItemVO copy(String virtualAbsolutePath, String targetVirtualAbsolutePath);
}
