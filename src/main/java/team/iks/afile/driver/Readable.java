package team.iks.afile.driver;

import java.io.OutputStream;
import java.util.List;

import team.iks.afile.pojo.vo.FileItemVO;

/**
 * 文件读操作
 *
 * @author vigork
 * At: 2023/1/1
 */
public interface Readable {
    /**
     * 获取目录下的所有文件, 当目录地址对应的文件夹不存在时抛出错误信息
     * @param virtualAbsolutePath 目录地址
     */
    List<FileItemVO> list(String virtualAbsolutePath);

    /**
     * 读取文件, 当文件路径所对应的文件不存在时抛出错误信息
     * @param virtualAbsolutePath 文件路径
     */
    OutputStream read(String virtualAbsolutePath);
}
