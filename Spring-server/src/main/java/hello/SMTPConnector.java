package hello;

import javax.mail.*;
import javax.mail.internet.AddressException;
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

    public void sendEmail(Session SMTPSession, String from, String to){
        try {
            MimeMessage message = new MimeMessage(SMTPSession);        // email message
            message.setFrom(new InternetAddress(from));                    // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Тестовое письмо от мега почтового клиента(еее-рок!)"); // subject line

            // actual mail body
            message.setText("Привет ${UserName}, чмок тебя в пупочек:*");

            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}


