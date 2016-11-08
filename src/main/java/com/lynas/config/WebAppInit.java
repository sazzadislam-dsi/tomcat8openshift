package com.lynas.config;

import com.lynas.AppConstant;
import com.lynas.security.AuthenticationTokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by LynAs on 23-Jan-16
 */
@Configuration
public class WebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        System.out.println("################################");
        System.out.println("##########Deploying####INVMREST#####On#######"+AppConstant.ENVIRONMENT+"###########");
        System.out.println("#######################################");
        servletContext.setInitParameter("spring.profiles.active", AppConstant.ENVIRONMENT);
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new AuthenticationTokenFilter()
        };
    }

}
