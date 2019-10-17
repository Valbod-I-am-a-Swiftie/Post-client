package hello;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

	private final UserRepository repository;

	HelloController(UserRepository repository) {
		this.repository = repository;
	}

    @RequestMapping("/greetings")
    public String get() {
        return "Greetings";
    }

	@GetMapping("/folders/{id}")
	public List<String> getFolders(@PathVariable Long id) throws Exception {
		User user = repository.findById(id).orElse(null);
		IMAPConnector connector = new IMAPConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
		return connector.getFolders();
	}

	@PostMapping("/mail/{id}")
	public void sendMail(@PathVariable Long id, @RequestBody PostMessage message) throws Exception{
		User user = repository.findById(id).orElse(null);
		SMTPConnector smtpConnector = new SMTPConnector(user.getMailLogin(), user.getMailPassword(), user.getSmtpAddr());
		smtpConnector.sendEmail(message);
		IMAPConnector imapConnector = new IMAPConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
		imapConnector.saveSendMessage(message);
	}

	@GetMapping("/mail/{id}")
	public List<PostMessage> getMail(@PathVariable Long id, @RequestParam(name = "folder", required = false, defaultValue = "INBOX") String folderName) throws Exception{
		User user = repository.findById(id).orElse(null);
		IMAPConnector imapConnector = new IMAPConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
		return imapConnector.getMailList(folderName);
	}

	@DeleteMapping("/mail/{id}")
	public void getMail(@PathVariable Long id, @RequestBody PostMessage message) throws Exception{
		User user = repository.findById(id).orElse(null);
		IMAPConnector imapConnector = new IMAPConnector(user.getMailLogin(), user.getMailPassword(), user.getImapAddr());
		imapConnector.deleteMessage(message);
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
