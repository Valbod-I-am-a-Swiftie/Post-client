package ikpi63holding.postclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TestDB implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public TestDB(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.deleteAll();

        User usor = new User("user1", 
                             passwordEncoder.encode("123456"),
                             "hello@google.org",
                             "mailPassword",
                             "smtpAddress",
                             665,
                             "imapAddress",
                             666,
                             "USER",
                             "");

        this.userRepository.save(usor);
    }
}