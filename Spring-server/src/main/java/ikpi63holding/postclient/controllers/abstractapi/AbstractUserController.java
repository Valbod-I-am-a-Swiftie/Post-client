package ikpi63holding.postclient.controllers.abstractapi;

import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractUserController extends AbstractDataController {

    public AbstractUserController(UserRepository userRepository) {
        super(userRepository);
    }

    /**
     * GET on '/username'
     *
     * @param username name to search by
     * @return user
     */
    public User findUser(@PathVariable String username) {
        return getUser(username);
    }

    /**
     * GET on '/'
     *
     * @return all users
     */
    // TODO paging
    public List<User> getAll() {
        return getAllUsers();
    }

    /**
     * POST on '/username'
     *
     * @param newUser user to add, not null, with unique name
     * @return added user
     */
    public User newUser(User newUser) {
        return saveUser(newUser);
    }

    /**
     * PUT on '/username'
     * Rewrites all data that can be changed
     *
     * @param newUser  new user data to save
     * @param username username to replace
     * @return changed user
     */
    public User replaceUser(User newUser, String username) {
        User user = getUser(username);

        if (!newUser.getUsername().equals(username) && userExists(newUser.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User with name " + newUser.getUsername() + "already exists");
        }

        if (newUser.getMailboxes() != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Updating nested resources from top entity is forbidden");
        }

        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        return saveUser(user);
    }

    /**
     * DELETE on '/'
     * FORBIDDEN
     */
    public void deleteAllUsers() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Deleting collections is forbidden");
    }

    /**
     * DELETE on '/username'
     * Only admin can delete user, user can only disable oneself by doing this
     *
     * @param username username to delete
     */
    public void deleteUser(String username) {
        User user = getUser(username);

        // To protect against accidental deletes
        //  @Max
        if (user.getActive() == 1) {
            user.setActive(0);
        } else {
            deleteUser(user);
        }

    }

}
