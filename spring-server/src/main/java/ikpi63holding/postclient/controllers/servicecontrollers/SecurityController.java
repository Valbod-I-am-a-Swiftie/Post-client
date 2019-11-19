package ikpi63holding.postclient.controllers.servicecontrollers;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.controllers.abstractapi.AbstractDataController;
import ikpi63holding.postclient.data.View;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
@Profile("!nojwt")
public class SecurityController extends AbstractDataController {

    @Autowired
    protected SecurityController(UserRepository userRepository) {
        super(userRepository);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.NewUser.class)
    User registration(@RequestBody User newUser) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userExists(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User " + newUser.getUsername() + " already exists");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles("USER");
        newUser.setPermissions("");
        newUser.setActive(1);

        return saveUser(newUser);
    }

}
