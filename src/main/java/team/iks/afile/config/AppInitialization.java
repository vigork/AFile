package team.iks.afile.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统初始化
 *
 * @author vigork
 * At: 2023/1/2
 */
@Component
@EnableConfigurationProperties(AppProperties.class)
@RequiredArgsConstructor
@Slf4j
public class AppInitialization implements BeanPostProcessor {
    private final AppProperties appProperties;

    public void initWorkspace() {
        File workspace = new File(appProperties.getWorkspace());
        if (!workspace.exists()) {
            boolean mkdir = workspace.mkdirs();
            if (mkdir) {
                log.info("成功初始化应用目录: [{}]", Paths.get(workspace.getAbsolutePath()).normalize());
            } else {
                log.error("初始化应用目录失败: [{}]", workspace);
            }
        }
    }

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        initWorkspace();
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
