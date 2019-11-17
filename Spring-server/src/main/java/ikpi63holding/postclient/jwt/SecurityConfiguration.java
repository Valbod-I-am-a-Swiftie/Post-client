package ikpi63holding.postclient.jwt;

import ikpi63holding.postclient.UriDefines;
import ikpi63holding.postclient.jwt.filters.JwtAuthenticationFilter;
import ikpi63holding.postclient.jwt.filters.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!nojwt")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtProperties jwtProperties;

    public SecurityConfiguration() {
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperties))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtProperties,
                        userDetailsService))
                .authorizeRequests()
                .antMatchers(UriDefines.ADMIN_API + UriDefines.ANY).hasRole("ADMIN")
                .antMatchers(UriDefines.USER_API, UriDefines.USER_API + UriDefines.ANY_LOCAL)
                .denyAll()
                .antMatchers(UriDefines.USER_API + UriDefines.USER_ENTITY,
                        UriDefines.USER_API + UriDefines.USER_ENTITY + UriDefines.ANY)
                .access("hasRole('USER') and " //FUCK THIS LINE
                        + "principal.getUsername().equals(#" + UriDefines.USER_VARIABLE + ")")
                .antMatchers(HttpMethod.POST, UriDefines.LOGIN, UriDefines.REGISTRATION).permitAll()
                .antMatchers(UriDefines.ROOT, UriDefines.ANY + ".html", UriDefines.ANY + ".css",
                        UriDefines.ANY + ".js", UriDefines.ANY + ".vue").permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}