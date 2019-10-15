package hello;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
public class HelloController {


    protected final UserRepository repository;

	HelloController(UserRepository repository) {
        this.repository = repository;
	}

    @RequestMapping("/greetings")
    @ResponseBody
    public String get() {
        return "Greetings";
    }

	@GetMapping("/users")
  	List<User> all() {
    	return repository.findAll();
  	}

  	@PostMapping("/users")
  	User newUser(@RequestBody User newUser) {
    	return repository.save(newUser);
 	}

 	// this one doesn't work correctly :c
 	@PutMapping("/users/{id}")
 	User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

    return repository.findById(id)
        .map(user -> {
            user.setLogin(newUser.getLogin());
            user.setPassword(newUser.getPassword());
            user.setMailLogin(newUser.getMailLogin());
            user.setMailPassword(newUser.getMailPassword());
            user.setSmtpAddr(newUser.getSmtpAddr());
            user.setSmtpPort(newUser.getSmtpPort());
            user.setImapAddr(newUser.getImapAddr());
            user.setImapPort(newUser.getImapPort());

            return repository.save(user);
        })
        .orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
  	}

  	@PostMapping("/users/{id}")
  	void deleteUser(@PathVariable Long id) {
   		repository.deleteById(id);
    }
}
