package team.iks.afile.util;

import java.util.function.Supplier;

import team.iks.afile.exception.ApplicationException;

/**
 * 参数处理工具类
 *
 * @author vigork
 * At: 2023/1/1
 */
public abstract class ArgUtils {

    /**
     * 非空校验
     */
    public static <T> void requireNonNull(T arg) {
        if (null == arg) {
            throw new ApplicationException("参数不能为空");
        }
    }

    /**
     * 设置默认值
     */
    public static <T> T withDefault(T arg, Supplier<T> valueSupplier) {
        if (null == arg) {
            return valueSupplier.get();
        }
        return arg;
    }
}
