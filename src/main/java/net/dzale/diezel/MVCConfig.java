package net.dzale.diezel;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configures view controllers and associated URL mappings.
 *
 * @author dzale
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/performance").setViewName("performance");
        registry.addViewController("/gramslist").setViewName("gramslist");
        registry.addViewController("/demo").setViewName("demo");
        registry.addViewController("/admin").setViewName("admin");
    }

}