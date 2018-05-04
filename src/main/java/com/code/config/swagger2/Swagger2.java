package com.code.config.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by MaJian on 18/2/18.
 * 自动生成API 文档
 *
 * @author majian
 *
 * 访问路径  项目名+swagger-ui.html  比如:ip:端口/项目名/swagger-ui.html
 *
 * http://blog.csdn.net/xupeng874395012/article/details/68946676  注解说明
 *
 *
 * API详细说明
 注释汇总
 作用范围	API	        使用位置
 对象属性	            @ApiModelProperty	用在出入参数对象的字段上
 协议集描述	        @Api	            用于controller类上
 协议描述  	        @ApiOperation	    用在controller的方法上
 Response集	        @ApiResponses	    用在controller的方法上
 Response	        @ApiResponse	    用在 @ApiResponses里边
 非对象参数集	        @ApiImplicitParams	用在controller的方法上
 非对象参数描述	    @ApiImplicitParam	用在@ApiImplicitParams的方法里边
 描述返回对象的意义	@ApiModel	        用在返回对象类上


 @ApiImplicitParam   这边参数要注意了
 属性	取值	作用
 paramType		查询参数类型
         path	以地址的形式提交数据
         query	直接跟参数完成自动映射赋值
         body	以流的形式提交 仅支持POST
         header	参数在request headers 里边提交
         form	以form表单的形式提交 仅支持POST
 dataType		参数的数据类型 只作为标志说明，并没有实际验证
         Long
         String
 name		接收参数名
 value		接收参数的意义描述
 required		参数是否必填
         true	必填
         false	非必填
 defaultValue		默认值
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${code.controller}")
    private String controller;
    @Bean
    public Docket createRestApi() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .description("项目文档")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controller))
                .paths(PathSelectors.any())
                .build();
    }
}
