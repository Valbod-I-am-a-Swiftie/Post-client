package ikpi63holding.postclient.controllers.userapi;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.UriDefines;
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
@RequestMapping(UriDefines.USER_API)
public class MailboxController extends AbstractMailboxController {

    @Autowired
    protected MailboxController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    @Override
    @PostMapping(UriDefines.MAILBOX_COLLECTION)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.NewMailbox.class)
    public Mailbox addMailbox(@RequestBody Mailbox newMailbox,
            @PathVariable(UriDefines.USER_VARIABLE) String username) {
        return super.addMailbox(newMailbox, username);
    }

    @Override
    @GetMapping(UriDefines.MAILBOX_ENTITY)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.Mailbox.class)
    public Mailbox findMailbox(@PathVariable(UriDefines.USER_VARIABLE) String username,
            @PathVariable(UriDefines.MAILBOX_VARIABLE) int mailboxId) {
        return super.findMailbox(username, mailboxId);
    }

    @Override
    @GetMapping(UriDefines.MAILBOX_COLLECTION)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.Compact.class)
    public List<Mailbox> allMailboxes(@PathVariable(UriDefines.USER_VARIABLE) String username) {
        return super.allMailboxes(username);
    }

    @Override
    @PostMapping(UriDefines.MAILBOX_ENTITY)
    @ResponseStatus(HttpStatus.OK)
    protected void sendMail(@PathVariable(UriDefines.USER_VARIABLE) String username,
            @PathVariable(UriDefines.MAILBOX_VARIABLE) int mailboxId,
            @RequestBody PostMessage message) throws Exception {
        super.sendMail(username, mailboxId, message);
    }

}
