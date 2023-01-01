package team.iks.afile.util;

/**
 * TODO: description
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class ArgUtils {
    public static <T> void requireNonNull(T arg) throws IllegalArgumentException {
        if (null == arg) {
            throw new IllegalArgumentException("参数不能为空");
        }
    }
}
