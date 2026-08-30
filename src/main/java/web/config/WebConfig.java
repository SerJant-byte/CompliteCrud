package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
// класс конфигурации Spring
@EnableWebMvc
//Включаем инфраструктуру Spring MVC для этого приложения
@ComponentScan("web")
// Просканируем пакет web.controller и найдем там Spring-компоненты,
// в том числе классы с @Controller
public class WebConfig {

    @Bean
    // Spring регистрирует обьект, который возвращаем и может с ним работать
    public SpringResourceTemplateResolver getTemplateResolver() {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setPrefix("/WEB-INF/views/");
        // где искать шаблоны
        springResourceTemplateResolver.setSuffix(".html");
        //какое расширение надо нам добавить
        springResourceTemplateResolver.setTemplateMode("HTML");
        //шаблоны у нас HTML
        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
        // кодировка, чтобы рус яз норм отображался, читаем символы в файле
        return springResourceTemplateResolver;
        // вернули настроенный обьект
    }

    @Bean
    public ThymeleafViewResolver getThymeleafViewResolver() {
        //задача данного бина - связать Spring MVC с Thymeleaf
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        // создаем движок для Thymeleaf
        //TemplateResolver → находит шаблон
        //TemplateEngine → обрабатывает шаблон
        //ViewResolver → связывает это со Spring MVC
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        // используем UTF 8 на уровне формируемого текста
        springTemplateEngine.setTemplateResolver(getTemplateResolver());
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        return thymeleafViewResolver;
    }


}
