package ikpi63holding.postclient.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("nojwt")
@EnableWebSecurity
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public NoSecurityConfiguration() {
        System.out.println("\nSecurity checks disabled\n");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .anyRequest().permitAll();

    }

}