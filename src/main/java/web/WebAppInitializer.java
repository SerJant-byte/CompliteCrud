package web;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import web.config.AppConfig;
import web.config.WebConfig;


import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        //root-конфигурация у нас — это AppConfig, потому что там находится инфраструктура приложения ->
        //DataSource
        //EntityManagerFactory
        //TransactionManager
        return new Class<?>[] {AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //getServletConfigClasses() сообщает DispatcherServlet,
        // какую конфигурацию использовать для веб-части приложения
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        //хотим, чтобы DispatcherServlet обрабатывал все запросы приложения -> поэтому "/"
        return new String[] {"/"};
        //Привяжи DispatcherServlet к /
    }

    @Override
    protected Filter[] getServletFilters() {
        // принудительно включаем кодировку utf 8
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8");
        characterEncodingFilter.setForceRequestEncoding(true);
        // принудительно использую кодировку для request
        characterEncodingFilter.setForceResponseEncoding(true);
        //принудительно использую кодировку для ответа
        return new Filter[]{characterEncodingFilter};
    }
}
