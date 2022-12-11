package uz.uzcard.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll();


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.cors().disable();
    }
}



