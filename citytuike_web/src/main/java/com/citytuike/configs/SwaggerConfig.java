package com.citytuike.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Component
public class SwaggerConfig {
    @Bean
    public Docket customDocket(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        ParameterBuilder NewTicketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("P-Token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();//header中的ticket参数非必填，传空也可以
        NewTicketPar.name("version").description("版本")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        pars.add(NewTicketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    /**
     * 这个是设置大标题小标题
     * @return
     */
    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("推客").description("前后端联调api 文档").version("0.1.0")
                .build();
    }


}
