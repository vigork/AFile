package team.iks.afile.exception;

/**
 * 文件操作异常
 *
 * @author vigork
 * At: 2023/1/1
 */
public class FileOperationException extends ApplicationException {
    private static final long serialVersionUID = -7383060285015939315L;

    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
