package hello;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SMTPConnector {
    private static String SMTPS_PORT = "465";


    static {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        try {
            ctx.init(new KeyManager[0], new TrustManager[] {new SMTPConnector.DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            System.out.println(e.toString());
        }
        SSLContext.setDefault(ctx);
    }

    public Session SMTPSession;
    SMTPConnector(String userName, String password, String host) throws Exception {
        this.SMTPSession = this.getSMTPSession( userName, password, host);
        SMTPSession.setDebug(true);
        SMTPSession.setDebugOut(System.out);
    }

    public Session getSMTPSession(String userName, String password, String host) throws Exception {

        if(userName == null){
            throw new Exception("No such user");
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", SMTPS_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"   );
        properties.put("mail.smtp.socketFactory.port", SMTPS_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session SMTPSession = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        return SMTPSession;
    }

    public void sendEmail(PostMessage message) throws MessagingException {
            Message email = message.toMessage(SMTPSession);
            System.out.println("Email converted");
            Transport.send(email);
            System.out.println("Email Sent successfully....");
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws
                CertificateException {}
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}


