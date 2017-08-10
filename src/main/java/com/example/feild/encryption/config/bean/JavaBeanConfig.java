package com.example.feild.encryption.config.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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

/**
 * 采用JavaConfig方式管理Bean<br>
 * 注意：因为MapperScannerConfigurer对象需要SqlSessionFactory对象的创建，所以添加@AutoConfigureAfter注解
 */
@Configuration
@AutoConfigureAfter(SqlSessionFactory.class)
public class JavaBeanConfig {

	/**
	 * Mybatis Mapper文件包名。
	 */
	private static final String MYBATIS_MAPPER_PACKAGE = "com.example.feild.encryption.domain.dao";

	/**
	 * 配置Mybatis动态代理DAO接口
	 * 
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(MYBATIS_MAPPER_PACKAGE);
		return mapperScannerConfigurer;
	}

	/**
	 * swagger2 配置
	 * 
	 * @author YuanZhiQiang
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		// 全局头部参数配置
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("header_param").description("全局头部参数").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

		// 全局公共参数配置
		ParameterBuilder aParameterBuilder1 = new ParameterBuilder();
		aParameterBuilder1.name("common_param").description("全局公共参数").modelRef(new ModelRef("string")).parameterType("query").required(false).build();

		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());
		aParameters.add(aParameterBuilder1.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.example.feild.encryption.domain.controller"))
				.paths(PathSelectors.any()).build().globalOperationParameters(aParameters);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("数据加密项目示例").description("这只是个Demo项目。").termsOfServiceUrl("http://git.xs.jf/xiangshang-micro/micro-order.git").build();
	}
}