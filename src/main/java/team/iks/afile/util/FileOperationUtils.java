package team.iks.afile.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import team.iks.afile.driver.FileTypeEnum;

/**
 * 文件操作工具类
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class FileOperationUtils {

    /**
     * 获取文件类型
     */
    public static FileTypeEnum getFileType(File file) {
        ArgUtils.requireNonNull(file);

        if (file.isDirectory()) {
            return FileTypeEnum.FOLDER;
        }

        // TODO: 根据后缀名获取文件类型

        return FileTypeEnum.OTHER;
    }

    public static String asAbsolutePath(String path) {
        return Optional.ofNullable(path)
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .map(p -> Paths.get(p).normalize().toString())
                .orElse(null);
    }
}
