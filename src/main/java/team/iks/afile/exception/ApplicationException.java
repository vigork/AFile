package team.iks.afile.exception;

/**
 * 应用异常
 *
 * @author vigork
 * At: 2023/1/1
 */
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 5015493921385429864L;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
