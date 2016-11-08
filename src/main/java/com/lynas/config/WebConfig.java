package com.lynas.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.Serializable;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan("com.lynas")
@EnableAspectJAutoProxy
@Scope("prototype")
public class WebConfig  implements Serializable{

    @Bean
    public Gson getGson(){
        return new Gson();
    }

    @Bean
    public static PlaceholderConfigurerSupport propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}

















