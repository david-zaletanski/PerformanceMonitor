package net.dzale.diezel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Configures the security / access settings.
 * https://spring.io/guides/gs/securing-web/#initial
 * 
 * @author zalet
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/console/**").permitAll()     // Allow H2 database browser console
            .antMatchers("/webjars/**").permitAll()		// Allow our libraries to be accessed.
            .antMatchers("/").hasRole("ADMIN") 			// Don't allow anyone to see page unless logged in.
            .antMatchers("/admin").hasRole("ADMIN")
            .and().formLogin().loginPage("/login").permitAll()
            // Warning: The following allows the logout link to be accessed via GET instead of POST, which is generally NOT recommended
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("admin").password("password").roles("ADMIN");
    }
}