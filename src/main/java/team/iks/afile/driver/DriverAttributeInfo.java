package team.iks.afile.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置项信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DriverAttributeInfo {
    /**
     * 标签名称
     */
    String label();

    /**
     * 配置项描述信息
     */
    String description() default "";

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 排序
     */
    int order() default 0;
}
