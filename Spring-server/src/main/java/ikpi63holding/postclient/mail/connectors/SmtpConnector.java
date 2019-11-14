package ikpi63holding.postclient.mail.connectors;

import ikpi63holding.postclient.mail.PostMessage;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SmtpConnector {
    private static String SMTPS_PORT = "465";

    static {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        try {
            ctx.init(new KeyManager[0],
                    new TrustManager[] {new SmtpConnector.DefaultTrustManager()},
                    new SecureRandom());
        } catch (KeyManagementException e) {
            System.out.println(e.toString());
        }
        SSLContext.setDefault(ctx);
    }

    public Session SmtpSession;

    public SmtpConnector(String username, String password, String host) throws Exception {
        this.SmtpSession = this.getSmtpSession(username, password, host);
    }

    private Session getSmtpSession(String username, String password, String host) throws Exception {

        if (username == null) {
            throw new Exception("No such user");
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", SMTPS_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", SMTPS_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendEmail(PostMessage message) throws MessagingException {
        Message email = message.toMessage(SmtpSession);
        System.out.println("Email converted");
        Transport.send(email);
        System.out.println("Email Sent successfully....");
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}


