package team.iks.afile.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动器信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DriverInfo {
    /**
     * 名称
     */
    String name();

    /**
     * 排序
     */
    int order() default Integer.MAX_VALUE;
}
