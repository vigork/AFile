package team.iks.afile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 应用配置
 *
 * @author vigork
 * At: 2023/1/2
 */
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String workspace;
}
