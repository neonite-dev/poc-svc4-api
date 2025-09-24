package com.cjt.svc4.config; 
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Value("${spring.jwt.header}")
        private String tokenRequestHeader; // "Authorization"

        @Bean
        public GroupedOpenApi extranetGroupedOpenApi() {
                return GroupedOpenApi
                                .builder()
                                .group("extranet") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                                .pathsToMatch("/api/extra/**") // group에 포함될 API endpoint 경로
                                .addOpenApiCustomizer(
                                        openApi -> openApi.setInfo(new Info()
                                                .title("extranet api") // API 제목
                                                .description("extranet 업무 처리를 위한 API") // API 설명
                                                .version("1.0.0") // API 버전
                                        ))
                                .build();
        }
    
    @Bean
    public GroupedOpenApi crmGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("crm") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                .pathsToMatch("/api/crm/**") // group에 포함될 API endpoint 경로
                .addOpenApiCustomizer(
                        openApi ->
                                openApi
                                        .setInfo(
                                                new Info()
                                                        .title("crm api") // API 제목
                                                        .description("crm 업무 처리를 위한 API") // API 설명
                                                        .version("1.0.0") // API 버전
                                        )
                )
                .build();
    }

    @Bean
    public GroupedOpenApi eduGroupedOpenApi() {
            return GroupedOpenApi
                            .builder()
                            .group("eduApp") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                            .pathsToMatch("/api/eduapp/**") // group에 포함될 API endpoint 경로
                            .addOpenApiCustomizer(
                                            openApi -> openApi
                                                            .setInfo(
                                                                            new Info()
                                                                                            .title("eduApp api") // API 제목
                                                                                            .description("eduApp 업무 처리를 위한 API") // API 설명
                                                                                            .version("1.0.0") // API 버전
                                                            ))
                            .build();
    }
    
        // API 보안 설정
        @Bean
        public OpenAPI api() {
                SecurityScheme apiKey = new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(tokenRequestHeader);
                SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");
                
                return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);
        }
}