package team.iks.afile.driver;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

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
                if (AbstractDriver.class.isAssignableFrom(aClass)) {
                    result.add(aClass);
                }
            } catch (Exception ignored) {}
        }

        return result;
    }

    /**
     * 获取已加载的存储库驱动信息
     */
    public List<DriverInfo> drivers() {
        return drivers.stream().map(driver -> new DriverInfo()
                .setName(driver.getSimpleName())
                .setDriver(driver)
        ).collect(Collectors.toList());
    }
}