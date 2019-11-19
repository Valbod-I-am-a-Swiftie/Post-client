package ikpi63holding.postclient.controllers.abstractapi;

import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailService;
import ikpi63holding.postclient.mail.MailServiceFactory;

public abstract class AbstractMailServiceController extends AbstractDataController {
    private final MailServiceFactory mailServiceFactory;

    protected AbstractMailServiceController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository);
        this.mailServiceFactory = mailServiceFactory;
    }

    protected MailService getMailService(Mailbox mailbox) throws Exception {
        return mailServiceFactory.getService(mailbox);
    }

}
