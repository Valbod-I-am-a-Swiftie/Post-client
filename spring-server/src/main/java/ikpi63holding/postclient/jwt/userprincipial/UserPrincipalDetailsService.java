package ikpi63holding.postclient.jwt.userprincipial;

import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.jwt.AdminProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("!nojwt")
public class UserPrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminProperties adminProperties;

    public UserPrincipalDetailsService(UserRepository userRepository,
            AdminProperties adminProperties) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.adminProperties = adminProperties;

        this.adminProperties.getAdministrators().forEach(admin -> {
            User user = new User();
            user.setUsername(admin.getName());
            user.setPassword(passwordEncoder.encode(admin.getPassword()));
            user.setRoles("ADMIN");
            user.setPermissions(admin.getPermissions());
            user.setActive(1);
            userRepository.save(user);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserPrincipal(this.userRepository.findByUsername(s).orElseThrow(
                () -> new UsernameNotFoundException("User " + s + " not found")
        ));
    }

}