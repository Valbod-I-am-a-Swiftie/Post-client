package ikpi63holding.postclient.controllers.userapi;

import ikpi63holding.postclient.UriDefines;
import ikpi63holding.postclient.controllers.abstractapi.AbstractMailServiceController;
import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;
import ikpi63holding.postclient.mail.PostMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriDefines.USER_API)
public class MailMessagingController extends AbstractMailServiceController {

    @Autowired
    protected MailMessagingController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    @GetMapping(UriDefines.MESSAGE_COLLECTION)
    @ResponseStatus(HttpStatus.OK)
    public List<PostMessage> getMailList(@PathVariable(UriDefines.USER_VARIABLE) String username,
            @PathVariable(UriDefines.MAILBOX_VARIABLE) int mailboxId,
            @PathVariable(UriDefines.FOLDER_VARIABLE) String folderName) throws Exception {
        Mailbox mailbox = getMailbox(username, mailboxId);
        var mailService = getMailService(mailbox);
        return mailService.getMailList(folderName);
    }

    @DeleteMapping(UriDefines.MESSAGE_ENTITY)
    public void deleteMail(@PathVariable(UriDefines.USER_VARIABLE) String username,
            @PathVariable(UriDefines.MAILBOX_VARIABLE) int mailboxId,
            @PathVariable(UriDefines.FOLDER_VARIABLE) String folderName, @PathVariable
            int messageId)
            throws Exception {
        Mailbox mailbox = getMailbox(username, mailboxId);
        var mailService = getMailService(mailbox);
        mailService.deleteMail(folderName, messageId);
    }

}
