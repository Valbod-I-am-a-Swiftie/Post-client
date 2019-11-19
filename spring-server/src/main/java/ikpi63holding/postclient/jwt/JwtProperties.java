package ikpi63holding.postclient.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Profile;

@ConstructorBinding
@ConfigurationProperties("jwt")
@Profile("!nojwt")
public class JwtProperties {
    public final String tokenPrefix;
    public final String headerString;
    public final String secret;
    public final int expirationTime;

    @Autowired
    public JwtProperties(String secret, @DefaultValue("864000000") int expirationTime,
            @DefaultValue("Bearer") String tokenPrefix,
            @DefaultValue("Authorization") String headerString) {
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.tokenPrefix = tokenPrefix + ' ';
        this.headerString = headerString;
    }

}