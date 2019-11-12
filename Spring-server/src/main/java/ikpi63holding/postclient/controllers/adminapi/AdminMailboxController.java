package ikpi63holding.postclient.controllers.adminapi;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.controllers.abstractapi.AbstractMailboxController;
import ikpi63holding.postclient.data.View;
import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;
import ikpi63holding.postclient.mail.PostMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/api/users")
public class AdminMailboxController extends AbstractMailboxController {

    @Autowired
    protected AdminMailboxController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    @Override
    @PostMapping("/{username}/mailboxes")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.NewMailboxAdmin.class)
    public Mailbox addMailbox(@RequestBody Mailbox newMailbox, @PathVariable String username) {
        return super.addMailbox(newMailbox, username);
    }

    @Override
    @GetMapping("/{username}/mailboxes/{mailboxId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.MailboxAdmin.class)
    public Mailbox findMailbox(@PathVariable String username, @PathVariable int mailboxId) {
        return super.findMailbox(username, mailboxId);
    }

    @Override
    @GetMapping("/{username}/mailboxes")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.AdminCompact.class)
    public List<Mailbox> allMailboxes(@PathVariable String username) {
        return super.allMailboxes(username);
    }

    @Override
    @PostMapping("/{username}/mailboxes/{mailboxId}")
    @ResponseStatus(HttpStatus.OK)
    protected void sendMail(@PathVariable String username, @PathVariable int mailboxId,
            @RequestBody PostMessage message) throws Exception {
        super.sendMail(username, mailboxId, message);
    }

}
