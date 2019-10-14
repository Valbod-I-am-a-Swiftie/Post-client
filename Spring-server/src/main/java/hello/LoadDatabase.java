package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
        repository.save(new User("sample", "pass", "sample@gmail.com", "qwerty", "smtp.google.com", 883, 
      		"imap.google.com", 666));
        };
    }
}