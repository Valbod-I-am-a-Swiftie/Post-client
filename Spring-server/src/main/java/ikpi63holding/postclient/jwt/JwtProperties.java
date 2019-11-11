package ikpi63holding.postclient.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConstructorBinding
@ConfigurationProperties("jwt")
public class JwtProperties {
    public final String tokenPrefix;
    public final String headerString;
    public final String secret;
    public final int expirationTime;

    public JwtProperties(String secret, @DefaultValue("864000000") int expirationTime,
            @DefaultValue("Bearer") String tokenPrefix,
            @DefaultValue("Authorization") String headerString){
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.tokenPrefix = tokenPrefix + ' ';
        this.headerString = headerString;
    }
}