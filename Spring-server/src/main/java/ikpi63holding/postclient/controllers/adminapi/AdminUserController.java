package ikpi63holding.postclient.controllers.adminapi;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.controllers.abstractapi.AbstractUserController;
import ikpi63holding.postclient.data.View;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/users")
public class AdminUserController extends AbstractUserController {

    @Autowired
    AdminUserController(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.UserAdmin.class)
    public User findUser(@PathVariable String username) {
        return super.findUser(username);
    }

    @Override
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.AdminCompact.class)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.NewUserAdmin.class)
    public User newUser(@RequestBody User newUser) {
        return super.newUser(newUser);
    }

    @Override
    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.NewUserAdmin.class)
    public User replaceUser(@RequestBody User newUser, @PathVariable String username) {
        return super.replaceUser(newUser, username);
    }

    @Override
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllUsers() {
        super.deleteAllUsers();
    }

    @Override
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String username) {
        super.deleteUser(username);
    }

}


