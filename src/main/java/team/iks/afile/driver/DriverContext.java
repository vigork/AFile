package team.iks.afile.driver;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import team.iks.afile.driver.annotation.DriverAttributeInfo;
import team.iks.afile.driver.annotation.DriverInfo;

import lombok.RequiredArgsConstructor;

/**
 * 驱动加载器
 *
 * @author vigork
 * At: 2023/1/2
 */
@Component
@RequiredArgsConstructor
public class DriverContext {
    private final List<Class<? extends AbstractDriver<?>>> drivers;

    public DriverContext() {
        this.drivers = getSystemDrivers();
    }

    /**
     * 加载系统内置存储库驱动
     * <p></p>
     * 将加载 <code>pkgName</code> 包下所有 {@link AbstractDriver} 的非抽象子类作为内置的存储库驱动器
     */
    private List<Class<? extends AbstractDriver<?>>> getSystemDrivers() {
        final String pkgName = this.getClass().getPackage().getName() + ".support";

        URL resource = Thread.currentThread().getContextClassLoader().getResource(pkgName.replace('.', '/'));
        File[] files = Optional.ofNullable(resource).map(r -> new File(r.getFile()).listFiles()).orElse(null);
        if (null == files) {
            return Collections.emptyList();
        }

        List<Class<? extends AbstractDriver<?>>> result = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory() || !fileName.endsWith(".class")) {
                continue;
            }

            String clzName = pkgName + "." + fileName.substring(0, fileName.length() - ".class".length());
            try {
                @SuppressWarnings("unchecked")
                Class<? extends AbstractDriver<?>> aClass = (Class<? extends AbstractDriver<?>>) Class.forName(clzName);
                if (!Modifier.isAbstract(aClass.getModifiers()) && AbstractDriver.class.isAssignableFrom(aClass)) {
                    result.add(aClass);
                }
            } catch (Exception ignored) {}
        }

        return result;
    }

    /**
     * 获取已加载的存储库驱动信息
     */
    public List<Driver> drivers() {
        return drivers.stream().map(driver -> {
            DriverInfo driverInfoAnnotation = driver.getAnnotation(DriverInfo.class);

            return new Driver()
                    .setName(Optional.ofNullable(driverInfoAnnotation).map(DriverInfo::name).orElse(driver.getSimpleName()))
                    .setDriver(driver)
                    .setAttributes(buildDriverAttributes(driver))
                    .setOrder(Optional.ofNullable(driverInfoAnnotation).map(DriverInfo::order).orElse(Integer.MAX_VALUE));
        }).collect(Collectors.toList());
    }

    private List<DriverAttribute> buildDriverAttributes(Class<? extends AbstractDriver<?>> driver) {
        Class<?> actualDriverAttributes = (Class<?>) ((ParameterizedTypeImpl) driver.getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] attributesDeclaredFields = actualDriverAttributes.getDeclaredFields();

        List<DriverAttribute> result = new ArrayList<>();
        for (Field attributesDeclaredField : attributesDeclaredFields) {
            DriverAttributeInfo driverAttributeInfoAnnotation = attributesDeclaredField.getAnnotation(DriverAttributeInfo.class);
            if (null == driverAttributeInfoAnnotation) {
                continue;
            }

            DriverAttribute driverAttributeInfo = new DriverAttribute()
                    .setLabel(driverAttributeInfoAnnotation.label())
                    .setName(attributesDeclaredField.getName())
                    .setDescription(driverAttributeInfoAnnotation.description())
                    .setRequired(driverAttributeInfoAnnotation.required())
                    .setOrder(driverAttributeInfoAnnotation.order());
            result.add(driverAttributeInfo);
        }

        return result;
    }
}
