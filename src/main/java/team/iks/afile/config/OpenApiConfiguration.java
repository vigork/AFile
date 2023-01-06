package team.iks.afile.config;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;

/**
 * 接口文档配置
 */
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPI(BuildProperties buildProperties) {
        return new OpenAPI().info(new Info().title(buildProperties.getName()).version(buildProperties.getVersion()));
    }

    @Bean
    public GroupedOpenApi storageApi() {
        return GroupedOpenApi.builder()
                .group("存储库")
                .pathsToMatch("/storage/**")
                .build();
    }
}
