package hello;

import javax.mail.*;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IMAPConnector {
    private static String IMAPS_PORT = "993";
    public Store store;

    static {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        try {
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            System.out.println(e.toString());
        }
        SSLContext.setDefault(ctx);
    }

    IMAPConnector(String login, String password, String host) throws Exception {

        this.store = this.getIMAPSession(login, password).getStore();
        store.connect( host,Integer.parseInt(IMAPS_PORT),login, password);
    }

    public Session getIMAPSession(String login, String password) throws Exception{

        if(login == null){
            throw new Exception("No such email");
        }
        Properties properties = new Properties();
        properties.put("mail.debug"          , "false"  );
        properties.put("mail.store.protocol" , "imaps"  );
        properties.put("mail.imap.ssl.enable", "true"   );
        properties.put("mail.imap.port"      , IMAPS_PORT);

        Session sessionIMAP = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        return sessionIMAP;
    }

    List<PostMessage> getMailList(String folderName) throws MessagingException, IOException {

        Folder folder = this.store.getFolder(folderName);
        if (folder.exists())
        {
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            List<PostMessage> ret = new ArrayList<>();

            for (Message message:messages) {
                ret.add(new PostMessage(message));
            }
            return ret;
        }
        return new ArrayList<>();
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }




}
