package hello;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SMTPConnector {
    private static String SMTPS_PORT = "465";

    public Session SMTPSession;
    SMTPConnector(String userName, String password, String host) throws Exception {
        this.SMTPSession = this.getSMTPSession( userName, password, host);
    }

    public Session getSMTPSession(String userName, String password, String host) throws Exception {

        if(userName == null){
            throw new Exception("No such user");
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", SMTPS_PORT);
        properties.put("mail.smtp.auth", "true");
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

    public void sendEmail(Session SMTPSession, PostMessage message){
        try {
            MimeMessage email = new MimeMessage(SMTPSession);

            //email.setFrom(new InternetAddress(message.));                    // setting header fields
            email.addRecipients(Message.RecipientType.TO, message.getAddresses());
            email.setSubject(message.getTitle()); // subject line

            // actual mail body
            email.setContent(message.getContent(), message.getContentType());

            // Send message
            Transport.send(email);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}


