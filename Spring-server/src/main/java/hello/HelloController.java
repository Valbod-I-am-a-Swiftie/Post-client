package hello;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class HelloController {

	private final UserRepository repository;

	HelloController(UserRepository repository) {
		this.repository = repository;
	}

  @ExceptionHandler(ResponseStatusException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No such user")
   String handleException(Exception ex) {
    return ex.toString();
  }

  @GetMapping("/users")
  	List<User> all() {
    	return repository.findAll();
  	}

  @GetMapping("/users/{id}")
  User uniqueUser(@PathVariable Long id) throws Exception {
    return repository.findById(id)
      .orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
  }

	@PostMapping("/users")
	User newUser(@RequestBody User newUser) {
  	return repository.save(newUser);
	}

 	// this one doesn't work correctly :c
 	@PutMapping("/users/{id}")
 	User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
    //only update, if doesn't exist - abandon
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

    //
  	@DeleteMapping("/users/{id}")
  	void deleteUser(@PathVariable Long id) {
   		repository.deleteById(id);
  	}
}
