package com.vma.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置swagger
 * Created by zhangsl.
 */
@EnableSwagger2
@Configuration
public class Swagger2 {

    /**
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {

        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("macKey").description("密钥").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vma.app"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    /**
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("app端接口        app-business")
                .description("接口文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    /**
     * @return RateLimiter
     */
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(0.5);
    }

}
