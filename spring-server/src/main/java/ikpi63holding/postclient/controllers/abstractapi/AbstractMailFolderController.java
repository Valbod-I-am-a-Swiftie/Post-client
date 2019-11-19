package ikpi63holding.postclient.controllers.abstractapi;

import ikpi63holding.postclient.data.folders.MailFolderSet;
import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;

public abstract class AbstractMailFolderController extends AbstractMailServiceController {

    protected AbstractMailFolderController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    /**
     * GET on '/'
     *
     * @param username  owner of mailbox
     * @param mailboxId id of mailbox
     * @return folders in mailbox
     */
    public MailFolderSet getFolders(String username, int mailboxId) {
        return getMailbox(username, mailboxId).getFolders();
    }

    /**
     * POST on '/'
     *
     * @param newMailFolderSet folders to add
     * @param username         owner of mailbox
     * @param mailboxId        id of mailbox
     * @return new folders
     * @throws Exception if connection failed
     */
    public MailFolderSet addFolders(MailFolderSet newMailFolderSet,
            String username, int mailboxId)
            throws Exception {
        User user = getUser(username);
        Mailbox mailbox = getMailbox(user, mailboxId);

        mailbox.setFolders(newMailFolderSet);
        var mailService = getMailService(mailbox);
        mailService.updateFolders();
        saveUser(user);
        return mailbox.getFolders();
    }

}
