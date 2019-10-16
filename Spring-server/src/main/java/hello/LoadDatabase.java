package hello;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class LoadDatabase implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public LoadDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // @Bean
    // CommandLineRunner initDatabase(UserRepository repository) {
    //     userRepository = repository;
    //     return args -> {
    //         repository.save(new User("sample", "pass", "sample@gmail.com", "qwerty", "smtp.google.com", 883, 
    //   		    "imap.google.com", 666, "USER", ""));
    //     };
    // }

    @Override
    public void run(String... args) {
        this.userRepository.deleteAll();

        User user1 = new User("sample", passwordEncoder.encode("123456"), "sample@gmail.com", "qwerty", "smtp.google.com", 883, 
                "imap.google.com", 666, "USER", "");
        User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@gmail.com", "123456", "smtp.google.com", 883, 
                "imap.google.com", 666, "USER", "");
        
        List<User> users = Arrays.asList(user1, admin);

        this.userRepository.saveAll(users);
    }
}