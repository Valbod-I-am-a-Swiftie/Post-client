package hello;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
class LoadDatabase implements CommandLineRunner {

    private UserRepository userRepository;

    public LoadDatabase(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User user1 = new User("sample", "pass", "sample@gmail.com", "qwerty", "smtp.google.com", 883, 
                "imap.google.com", 666, "USER", "");
        User admin = new User("admin", "admin123", "admin@gmail.com", "123456", "smtp.google.com", 883, 
                "imap.google.com", 666, "USER", "");
        
        List<User> users = Arrays.asList(user1, admin);

        this.userRepository.saveAll(users);
    }
}