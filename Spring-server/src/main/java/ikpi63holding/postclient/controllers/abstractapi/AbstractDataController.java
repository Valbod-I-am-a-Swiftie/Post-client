package ikpi63holding.postclient.controllers.abstractapi;

import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractDataController {

    private final UserRepository userRepository;

    protected AbstractDataController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + username));
    }

    protected List<User> getAllUsers() {
        return userRepository.findAll();
    }

    protected User saveUser(User user) {
        return userRepository.save(user);
    }

    protected boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    protected void deleteUser(User user){
        userRepository.delete(user);
    }

    protected Mailbox getMailbox(String username, int mailboxId) {
        User user = getUser(username);
        return getMailbox(user, mailboxId);
    }

    protected Mailbox getMailbox(User user, int mailboxId) {
        return user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + user.getUsername() + " with id " + mailboxId));
    }

}
