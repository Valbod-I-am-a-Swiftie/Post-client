package ikpi63holding.postclient.jwt.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ikpi63holding.postclient.jwt.JwtProperties;
import ikpi63holding.postclient.jwt.userprincipial.UserPrincipalDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtProperties jwtProperties;
    private final UserPrincipalDetailsService principalDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
            JwtProperties jwtProperties, UserPrincipalDetailsService detailsService) {
        super(authenticationManager);
        this.jwtProperties = jwtProperties;
        this.principalDetailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtProperties.headerString);

        if (header == null || !header.startsWith(jwtProperties.tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.headerString)
                .replace(jwtProperties.tokenPrefix, "");

        if (token != null) {
            String userName = JWT.require(Algorithm.HMAC512(jwtProperties.secret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            if (userName != null) {
                UserDetails userDetails = principalDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userName, null,
                                userDetails.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

}