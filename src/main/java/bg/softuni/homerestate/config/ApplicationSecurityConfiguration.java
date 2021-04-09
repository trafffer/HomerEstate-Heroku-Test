package bg.softuni.homerestate.config;

import bg.softuni.homerestate.services.HomerDBUserService;
import bg.softuni.homerestate.web.GlobalAdvise;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder encoder;
    private final HomerDBUserService homerDBUserService;

    public ApplicationSecurityConfiguration(PasswordEncoder encoder, HomerDBUserService homerDBUserService) {
        this.encoder = encoder;
        this.homerDBUserService = homerDBUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**","/css/**","/img/**").permitAll()
                .antMatchers("/roles/**","/offers/delete/**","/comments/delete/**").hasRole("ADMIN")
                .antMatchers("/","/users/login","/users/register","/about","/contact","/currency/api").permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                //this parameters can be String("username") and ("password")
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error")
                .and()
                .exceptionHandling().accessDeniedPage("/error-403")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(homerDBUserService).passwordEncoder(encoder);
    }
}
