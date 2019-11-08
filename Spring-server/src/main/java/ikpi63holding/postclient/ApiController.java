package ikpi63holding.postclient;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;

    private final MailboxRepository mailboxRepository;

    @Autowired
    private MailServiceFactory mailServiceFactory;

    @Autowired
    ApiController(UserRepository userRepository, MailboxRepository mailboxRepository) {
        this.userRepository = userRepository;
        this.mailboxRepository = mailboxRepository;
    }

    @PostMapping("/users/{userId}/mailboxes/{mailboxId}")
    public void sendMail(@PathVariable Long userId, @PathVariable Long mailboxId,
            @RequestBody PostMessage message) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));

        var mailService = mailServiceFactory.getService(mailbox);
        mailService.sendMail(message);

    }

    @GetMapping("/users/{userId}/mailboxes/{mailboxId}/folders/{folderName}/mail")
    @ResponseStatus(HttpStatus.OK)
    public List<PostMessage> getMailList(@PathVariable Long userId, @PathVariable Long mailboxId,
            @PathVariable String folderName) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));

        var mailService = mailServiceFactory.getService(mailbox);
        return mailService.getMailList(folderName);
    }

    @GetMapping("/users/{userId}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    public MailFolderSet getFolders(@PathVariable Long userId, @PathVariable Long mailboxId)
            throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));

        var mailService = mailServiceFactory.getService(mailbox);
        mailService.updateFolders();
        return mailboxRepository.save(mailbox).getFolders();
    }

    @PutMapping("/users/{userId}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    public MailFolderSet putFolders(@RequestBody MailFolderSet newMailFolderSet,
            @PathVariable Long userId, @PathVariable Long mailboxId)
            throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));
        mailbox.setFolders(newMailFolderSet);
        var mailService = mailServiceFactory.getService(mailbox);
        mailService.updateFolders();
        return mailboxRepository.save(mailbox).getFolders();
    }

    @GetMapping("/users/{userId}/mailboxes/{mailboxId}")
    public Mailbox getMailbox(@PathVariable Long userId, @PathVariable Long mailboxId)
            throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));

        var mailService = mailServiceFactory.getService(mailbox);
        return mailboxRepository.save(mailbox);
    }

    @DeleteMapping("/users/{userId}/mailboxes/{mailboxId}/folders/{folderName}/mail/{mailId}")
    public void deleteMail(@PathVariable Long userId, @PathVariable Long mailboxId,
            @PathVariable String folderName, @PathVariable
            int mailId)
            throws Exception {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such mailbox"));
        var mailService = mailServiceFactory.getService(mailbox);
        mailService.deleteMail(folderName, mailId);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    User uniqueUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users/{id}/mailboxes")
    @ResponseStatus(HttpStatus.CREATED)
    List<Mailbox> addMailbox(@RequestBody Mailbox newMailbox, @PathVariable Long id) {
        var r = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
        r.addMailbox(newMailbox);
        return userRepository.save(r).getMailboxes();
    }

    @GetMapping("/users/{id}/mailboxes")
    @ResponseStatus(HttpStatus.OK)
    List<Mailbox> allMailboxes(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"))
                .getMailboxes();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void deleteAllUsers() {
        // It's forbidden to delete the whole collection
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user");
        }
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    User registration(@RequestBody User newUser) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        if (this.repository.findByUsername(newUser.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              "User " + newUser.getUsername() + " already exists");
        }

        return repository.save(new User(
            newUser.getUsername(),
            passwordEncoder.encode(newUser.getPassword()),
            newUser.getMailLogin(),
            newUser.getMailPassword(),
            newUser.getSmtpAddr(),
            newUser.getSmtpPort(),
            newUser.getImapAddr(),
            newUser.getImapPort(),
            "USER",
            ""
        ));
    }

}
