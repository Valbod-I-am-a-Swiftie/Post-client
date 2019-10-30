package ikpi63holding.postclient;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApiController {

    private final UserRepository repository;

    ApiController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/folders/{id}")
    public List<String> getFolders(@PathVariable Long id) throws Exception {
        User user = repository.findById(id).orElse(null);
        ImapConnector connector =
                new ImapConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
        return connector.getFolders();
    }

    @PostMapping("/mail/{id}")
    public void sendMail(@PathVariable Long id, @RequestBody PostMessage message) throws Exception {
        User user = repository.findById(id).orElse(null);
        SmtpConnector smtpConnector =
                new SmtpConnector(user.getMailLogin(), user.getMailPassword(), user.getSmtpAddr());
        smtpConnector.sendEmail(message);
        ImapConnector imapConnector =
                new ImapConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
        imapConnector.saveSendMessage(message);
    }

    @GetMapping("/mail/{id}")
    public List<PostMessage> getMail(@PathVariable Long id,
            @RequestParam(name = "folder", required = false, defaultValue = "INBOX")
                    String folderName) throws Exception {
        User user = repository.findById(id).orElse(null);
        ImapConnector imapConnector =
                new ImapConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
        return imapConnector.getMailList(folderName);
    }

    @DeleteMapping("/mail/{id}")
    public void deleteMail(@PathVariable Long id, @RequestBody PostMessage message) throws Exception {
        User user = repository.findById(id).orElse(null);
        ImapConnector imapConnector =
                new ImapConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
        imapConnector.deleteMessage(message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No such user")
    String handleNotFoundException(ResponseStatusException ex) {
        return ex.toString();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    User uniqueUser(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setMailLogin(newUser.getMailLogin());
                    user.setMailPassword(newUser.getMailPassword());
                    user.setSmtpAddr(newUser.getSmtpAddr());
                    user.setSmtpPort(newUser.getSmtpPort());
                    user.setImapAddr(newUser.getImapAddr());
                    user.setImapPort(newUser.getImapPort());

                    return repository.save(user);
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user");
        }
    }

}
