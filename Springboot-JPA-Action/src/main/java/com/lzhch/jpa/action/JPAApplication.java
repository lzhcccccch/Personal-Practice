package com.lzhch.jpa.action;

import com.bcp.sdp.jpa.base.persistence.impl.BaseRepositoryImpl;
import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @packageName： com.lzhch.jpa.action
 * @className: JPAApplication
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-12-29 15:57
 */
@SpringBootApplication
@EnableJpaAuditing // 开启此注解才可正常使用 @CreateTime @UpdateTime 注解
// 将 Jpa 默认的仓库指定为自己定义的 BaseRepositoryImpl 因为已经继承 SimpleJpaRepository 所以可以用里面所有方法
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableSwagger2
@ComponentScan({"com.bcp.sdp", "com.lzhch.jpa.action"})
public class JPAApplication {

    public static void main(String[] args) {
        SpringApplication.run(JPAApplication.class, args);
        System.out.println("------start-------");
    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定要暴露给 swagger 来展示的接口所在的包 RequestHandlerSelectors.basePackage("com.lzhch")可以指定包 any 是所有
                .apis(RequestHandlerSelectors.any())
                // PathSelectors.any() 表示所有路径都放行
                .paths(Predicates.or(
                        // 过滤显示接口
                        PathSelectors.regex("/users" + ".*"),
                        PathSelectors.regex("/pack/service/" + ".*")
                )).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 标题
                .title("System API Doc")
                // 描述
                .description("系统接口测试.")
                // 联系人
                .contact(new Contact("liuzhichao", null,"www.liuzhichao00@163.com"))
                // 版本
                .version("1.0")
                // 服务地址, 可以在 swagger 界面点击链接进行跳转
                .termsOfServiceUrl("http://www.baidu.com")
                .build();
    }

}
