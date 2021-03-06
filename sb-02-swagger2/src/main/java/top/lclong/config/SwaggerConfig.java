package top.lclong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String environment;

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "top.lclong";

    Contact myContact = new Contact("", "", "");

    /**
     * 配置首页信息
     *
     * @return
     */
    private ApiInfo myApiInfo() {
        return new ApiInfo("Api Documentation",
                "Api Documentation",
                "1.0",
                "urn:tos",
                myContact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    /**
     * 配置 Swagger 的 Docket 的 bean 实例
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(myApiInfo())
                // enable: 是否能访问swagger，可以通过flag使swagger只在生产环境下使用
                .enable(this.ifDevEnvironment())
                .groupName("lcl")
                .select()
                // RequestHandlerSelectors 选择扫描方式
                // basePackage() 配置要扫描的包
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                // path() 配置要扫描的路径
                .build();
    }

    public Boolean ifDevEnvironment() {
        return this.environment.equals("dev");
    }

}
