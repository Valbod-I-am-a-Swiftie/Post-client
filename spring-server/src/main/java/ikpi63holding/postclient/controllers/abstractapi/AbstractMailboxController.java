package ikpi63holding.postclient.controllers.abstractapi;

import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailService;
import ikpi63holding.postclient.mail.MailServiceFactory;
import ikpi63holding.postclient.mail.PostMessage;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractMailboxController extends AbstractMailServiceController {

    protected AbstractMailboxController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    /**
     * POST on '/'
     *
     * @param newMailbox mailbox to add
     * @param username   owner of mailbox
     * @return added mailbox
     */
    public Mailbox addMailbox(Mailbox newMailbox, String username) {
        User user = getUser(username);
        if (user.mailboxExists(newMailbox.getMailLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    newMailbox.getMailLogin() + " already exists for " + username);
        }
        user.addMailbox(newMailbox);
        return saveUser(user).getMailbox(newMailbox.getMailLogin()).orElse(null);
    }

    /**
     * GET on '/'
     *
     * @param username owner of mailboxes
     * @return all mailboxes owned by user
     */
    public List<Mailbox> allMailboxes(String username) {
        return getUser(username).getMailboxes();
    }

    /**
     * GET on '/mailboxId'
     *
     * @param username  owner of mailbox
     * @param mailboxId mailbox id
     * @return found mailbox
     */
    public Mailbox findMailbox(String username, int mailboxId) {
        return getMailbox(username, mailboxId);
    }

    /**
     * POST on '/mailboxId'
     * Sends mail from this mailbox
     *
     * @param username  mailbox owner
     * @param mailboxId mailbox id
     * @param message   message to send
     * @throws Exception if can't send message
     */
    // TODO: remake this logic, I think we should put emails to folder
    //  and decide on action based on folder type
    //  @Max
    protected void sendMail(String username, int mailboxId,
            PostMessage message) throws Exception {
        Mailbox mailbox = getMailbox(username, mailboxId);
        MailService mailService = getMailService(mailbox);
        mailService.sendMail(message);
    }

}
