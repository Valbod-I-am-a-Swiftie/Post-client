package ikpi63holding.postclient;

import java.util.List;

public class MailService {

    private Mailbox mailbox = null;

    private ImapConnector imapConnector;
    private SmtpConnector smtpConnector;

    public MailService(Mailbox mailbox) throws Exception {
        init(mailbox);
    }

    private void init(Mailbox mailbox) throws Exception {
        if (this.mailbox != null) {
            throw new Exception("Mailbox already initialised");
        }
        if (mailbox == null) {
            throw new Exception("Null is not allowed");
        }
        this.mailbox = mailbox;
        this.imapConnector = new ImapConnector(mailbox.getMailLogin(), mailbox.getMailPassword(),
                mailbox.getImapAddr());
        this.smtpConnector = new SmtpConnector(mailbox.getMailLogin(), mailbox.getMailPassword(),
                mailbox.getSmtpAddr());
    }

    public void updateFolders() throws Exception {
        var newFolders = mailbox.getFolders();
        var actualFolders = imapConnector.getFolders();
        newFolders.updateSet(actualFolders);
        mailbox.setFolders(newFolders);
    }

    public void sendMail(PostMessage message) throws Exception {
        var folders = mailbox.getFolders();
        String sentFolder = folders.getFolder(FolderType.SENT);
        smtpConnector.sendEmail(message);
        imapConnector.saveMessage(sentFolder, message);
    }

    public List<PostMessage> getMailList(String folderName) throws Exception {
        if (!mailbox.getFolders().contains(folderName)) {
            throw new Exception("No such folder");
        }
        return imapConnector.getMailList(folderName);

    }

    public void deleteMail(String folderName, int messageId)
            throws Exception {
        var folders = mailbox.getFolders();
        if (!mailbox.getFolders().contains(folderName)) {
            throw new Exception("No such folder");
        }
        String trashFolder = folders.getFolder(FolderType.TRASH);
        if (folderName.equals(trashFolder)) {
            imapConnector.deleteMessage(folderName, messageId);
        } else {
            imapConnector.moveMessage(folderName, trashFolder, messageId);
        }
    }

}
