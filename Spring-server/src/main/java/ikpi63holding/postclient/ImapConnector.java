package ikpi63holding.postclient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ImapConnector {
    private static String IMAPS_PORT = "993";

    static {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        try {
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()},
                    new SecureRandom());
        } catch (KeyManagementException e) {
            System.out.println(e.toString());
        }
        SSLContext.setDefault(ctx);
    }

    private Session session;
    private Store store;

    ImapConnector(String login, String password, String host) throws Exception {

        this.session = getImapSession(login, password);
        this.store = this.session.getStore();
        store.connect(host, Integer.parseInt(IMAPS_PORT), login, password);
    }

    public Session getImapSession(String login, String password) throws Exception {

        if (login == null) {
            throw new Exception("No such email");
        }
        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAPS_PORT);

        Session sessionImap =
                Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });
        return sessionImap;
    }

    public List<String> getFolders() throws MessagingException {
        return getAllFolders(this.store.getDefaultFolder());
    }

    private List<String> getAllFolders(Folder folder) throws MessagingException {
        List<String> folders = new ArrayList<>() {{
            add(folder.getFullName());
        }};
        for (var nested : folder.list()) {
            folders.addAll(getAllFolders(nested));
        }
        return folders;
    }

    List<PostMessage> getMailList(String folderName) throws MessagingException, IOException {

        Folder folder = this.store.getFolder(folderName);
        if (folder.exists()) {
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            List<PostMessage> ret = new ArrayList<>();

            for (Message message : messages) {
                ret.add(new PostMessage(message));
            }
            return ret;
        }
        return new ArrayList<>();
    }

    InputStream getFileFromMessage(String folderName, int messageNumber, String filename) throws NullPointerException,MessagingException, IOException {
        Folder folder = this.store.getFolder(folderName);
        if (folder.exists()) {
            folder.open(Folder.READ_ONLY);

            Message message = folder.getMessage(messageNumber);
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            BodyPart bodyPart;

            for (int i = 0; i < mimeMultipart.getCount(); i++) {
                bodyPart = mimeMultipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    if (bodyPart.getFileName().equals(filename)) {
                        return bodyPart.getInputStream();
                    }
                }
            }

        }
        return null;
    }

    public void saveSendMessage(PostMessage message) throws MessagingException {
        Folder folder = this.store.getFolder("SentBox");
        folder.open(Folder.READ_WRITE);
        Message email = message.toMessage(session);
        folder.appendMessages(new Message[] {email});
    }

    public void deleteMessage(PostMessage message) throws MessagingException {
        Folder folder = store.getFolder(message.getFolder());
        folder.open(Folder.READ_WRITE);
        Message email = folder.getMessage(message.getNumberInFolder());
        if (!message.getFolder().equals("Trash")) {
            Folder trashFolder = store.getFolder("Trash");
            trashFolder.open(Folder.READ_WRITE);
            folder.copyMessages(new Message[] {email}, trashFolder);
        }
        email.setFlag(Flags.Flag.DELETED, true);
        folder.expunge();
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}
