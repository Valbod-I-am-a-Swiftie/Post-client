package ikpi63holding.postclient.jwt.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.jwt.JwtProperties;
import ikpi63holding.postclient.jwt.userprincipial.UserPrincipal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProperties jwtProperties;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        // We can set login endpoint here
        //setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/logme", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        User credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        new ArrayList<>());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult) {

        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.expirationTime))
                .sign(Algorithm.HMAC512(jwtProperties.secret.getBytes()));

        response.addHeader(jwtProperties.headerString, jwtProperties.tokenPrefix + token);
    }

}