package com.example.feild.encryption.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 采用JavaConfig方式管理Bean<br>
 * 注意：因为MapperScannerConfigurer对象需要SqlSessionFactory对象的创建，所以添加@AutoConfigureAfter注解
 */
@Configuration
public class JavaBeanConfig {

	/**
	 * swagger2 配置
	 */
	@Bean
	public Docket createRestApi() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("数据加密项目示例").description("这只是个Demo项目。").termsOfServiceUrl("http://git.xs.jf/xiangshang-micro/micro-order.git").build();
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select().apis(RequestHandlerSelectors.basePackage("com.example.feild.encryption.domain.controller"))
				.paths(PathSelectors.any()).build();
	}
}