package ikpi63holding.postclient.jwt;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;


@ConfigurationProperties("administration")
@Profile("!nojwt")
@ToString
@RequiredArgsConstructor
public class AdminProperties {

    @Getter
    private final List<Admin> administrators;

    @ToString
    @RequiredArgsConstructor
    public static class Admin{

        @Getter
        private final String name;

        @Getter
        private final String password;

        @Getter
        private final String permissions;

    }
}
