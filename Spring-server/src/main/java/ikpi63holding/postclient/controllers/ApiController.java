package ikpi63holding.postclient.controllers;

import ikpi63holding.postclient.data.folders.MailFolderSet;
import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;
import ikpi63holding.postclient.mail.PostMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;

    @Autowired
    private MailServiceFactory mailServiceFactory;

    @Autowired
    ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userName}/mailboxes/{mailboxId}")
    public void sendMail(@PathVariable String userName, @PathVariable int mailboxId,
            @RequestBody PostMessage message) throws Exception {
        // TODO: remake this logic, I think we should put emails to folder
        //  and decide on action based on folder type
        //  @Max
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        Mailbox mailbox = user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));

        var mailService = mailServiceFactory.getService(mailbox);
        mailService.sendMail(message);
    }

    @GetMapping("/users/{userName}/mailboxes/{mailboxId}/folders/{folderName}/mail")
    @ResponseStatus(HttpStatus.OK)
    public List<PostMessage> getMailList(@PathVariable String userName, @PathVariable int mailboxId,
            @PathVariable String folderName) throws Exception {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        Mailbox mailbox = user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));

        var mailService = mailServiceFactory.getService(mailbox);
        return mailService.getMailList(folderName);
    }

    @GetMapping("/users/{userName}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    public MailFolderSet getFolders(@PathVariable String userName, @PathVariable int mailboxId) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        return user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId)).getFolders();
    }

    @PutMapping("/users/{userName}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    public MailFolderSet putFolders(@RequestBody MailFolderSet newMailFolderSet,
            @PathVariable String userName, @PathVariable int mailboxId)
            throws Exception {
        // TODO: this seems to make more sense as a POST request
        //  @Max
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        Mailbox mailbox = user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));

        mailbox.setFolders(newMailFolderSet);
        var mailService = mailServiceFactory.getService(mailbox);
        mailService.updateFolders();
        userRepository.save(user);
        return mailbox.getFolders();
    }

    @GetMapping("/users/{userName}/mailboxes/{mailboxId}")
    public Mailbox getMailbox(@PathVariable String userName, @PathVariable int mailboxId) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        return user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));
    }

    @DeleteMapping("/users/{userName}/mailboxes/{mailboxId}/folders/{folderName}/mail/{mailId}")
    public void deleteMail(@PathVariable String userName, @PathVariable int mailboxId,
            @PathVariable String folderName, @PathVariable
            int mailId)
            throws Exception {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        Mailbox mailbox = user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));

        var mailService = mailServiceFactory.getService(mailbox);
        mailService.deleteMail(folderName, mailId);
    }

    @GetMapping("/users/{userName}")
    @ResponseStatus(HttpStatus.OK)
    User uniqueUser(@PathVariable String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No user with name" + userName));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users/{userName}/mailboxes")
    @ResponseStatus(HttpStatus.CREATED)
    List<Mailbox> addMailbox(@RequestBody Mailbox newMailbox, @PathVariable String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        user.addMailbox(newMailbox);
        return userRepository.save(user).getMailboxes();
    }

    @GetMapping("/users/{userName}/mailboxes")
    @ResponseStatus(HttpStatus.OK)
    List<Mailbox> allMailboxes(@PathVariable String userName) {
        return userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName))
                .getMailboxes();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PutMapping("/users/{userName}")
    @ResponseStatus(HttpStatus.OK)
    User replaceUser(@RequestBody User newUser, @PathVariable String userName) {
        return userRepository.findByUsername(userName)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No user with name" + userName));
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void deleteAllUsers() {
        // It's forbidden to delete the whole collection
    }

    @DeleteMapping("/users/{userName}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with name" + userName));

        // To protect against accidental deletes
        //  @Max
        user.setActive(0);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    User registration(@RequestBody User newUser) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User " + newUser.getUsername() + " already exists");

        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles("USER");
        newUser.setPermissions("");
        newUser.setActive(1);

        return userRepository.save(newUser);
    }

}
