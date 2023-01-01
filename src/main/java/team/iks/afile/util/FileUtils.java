package team.iks.afile.util;

import java.io.File;

import team.iks.afile.storage.FileTypeEnum;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class FileUtils {
    public static FileTypeEnum getFileType(File file) {
        ArgUtils.requireNonNull(file);

        if (file.isDirectory()) {
            return FileTypeEnum.FOLDER;
        }

        return FileTypeEnum.OTHER;
    }
}
