package team.iks.afile.common;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统初始化
 *
 * @author vigork
 * At: 2023/1/2
 */
@Component
@Slf4j
public class ApplicationInitialization implements BeanPostProcessor {
    @Value("${user.home}")
    private String userHome;

    public void initAppDir() {
        File appDir = new File(userHome, "/.afile");
        if (!appDir.exists()) {
            boolean mkdir = appDir.mkdir();
            if (mkdir) {
                log.info("成功初始化应用目录: [{}]", Paths.get(appDir.getAbsolutePath()).normalize());
            } else {
                log.error("初始化应用目录失败: [{}]", appDir);
            }
        }
    }

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        initAppDir();
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
