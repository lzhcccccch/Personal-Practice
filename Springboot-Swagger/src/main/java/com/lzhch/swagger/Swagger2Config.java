package com.lzhch.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @packageName： com.lzhch.swagger
 * @className: Swagger2Config
 * @description:  使用 swagger 文档, 使用配置类或者将代码直接当如启动类中
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-22 11:15
 */
//@EnableSwagger2
//@Configuration // 使用配置类的方式需要启用这两个注解 暂时使用直接放到启动类中的方式
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定要暴露给 swagger 来展示的接口所在的包 RequestHandlerSelectors.basePackage("com.lzhch")可以指定包 any 是所有
                .apis(RequestHandlerSelectors.any())
                // PathSelectors.any() 表示所有路径都放行
                .paths(
                        PathSelectors.any()
                        //Predicates.or(
                        //// 过滤显示接口
                        //PathSelectors.regex("/users" + ".*"),
                        //PathSelectors.regex("/url" + ".*")
                        //)
                )
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 标题
                .title("System API Doc")
                // 描述
                .description("系统接口测试.")
                // 联系人
                .contact(new Contact("liuzhichao", null,"www.liuzhichao00@163.com"))
                // 服务地址
                .termsOfServiceUrl("http://localhost:8090/doc.html")
                // 版本
                .version("1.0")
                .build();
    }

}
