package team.iks.afile.common;

/**
 * 文件操作异常
 *
 * @author vigork
 * At: 2023/1/1
 */
public class FileOperationException extends RuntimeException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
