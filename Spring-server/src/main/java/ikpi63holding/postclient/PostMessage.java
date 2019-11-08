package ikpi63holding.postclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class PostMessage {

    private int numberInFolder;
    private String title;
    private Object content;
    private String contentType;
    private Date date;
    private String[] from;
    private String[] recipients;
    private String folder;

    public PostMessage(String title, String content, String contentType, Date date, String[] from,
            String[] recipients, String folder) {
        this.title = title;
        this.content = content;
        this.contentType = contentType;
        this.date = date;
        this.from = from;
        this.recipients = recipients;
        this.folder = folder;
    }

    public PostMessage(Message message) throws MessagingException, IOException {
        this.title = message.getSubject();
        this.content = message.getContent();
        this.contentType = message.getContentType();
        this.numberInFolder = message.getMessageNumber();
        this.date = message.getSentDate();
        this.from = mapToString(message.getFrom());
        this.recipients = mapToString(message.getAllRecipients());
        this.folder = message.getFolder().getFullName();
    }

    public Message toMessage(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.addFrom(mapToAddress(this.from));
        message.addRecipients(Message.RecipientType.TO, mapToAddress(this.recipients));
        message.setSubject(this.title);
        message.setContent(this.content, this.contentType);
        if (date != null) {
            message.setSentDate(date);
        }
        return message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getContent() {

        List<String> files = new ArrayList<>();
        if (this.contentType.contains("text") || this.contentType.contains("html")) {
            return content.toString();
        }
        if (this.contentType.contains("multipart")) {
            Map<String,Object> ret = new HashMap<>();
            MimeMultipart mimeMultipart = (MimeMultipart) content;
            BodyPart bodyPart;
            int count = 0;
            try {
                count = mimeMultipart.getCount();
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < count; i++) {
                try {
                    bodyPart = mimeMultipart.getBodyPart(i);
                    if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {

                        String fileName = MimeUtility.decodeText(bodyPart.getFileName());

                        files.add(folder + "/" + numberInFolder + "/" + bodyPart.getFileName());
                    } else {
                        ret.put(Integer.toString(i),bodyPart.getContent());
                    }
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }
            ret.put("files",files);
            return ret;
        }

        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getFrom() {
        return from;
    }

    public void setFrom(String[] addresses) {
        this.from = addresses;
    }

    public int getNumberInFolder() {
        return numberInFolder;
    }

    public void setNumberInFolder(int numberInFolder) {
        this.numberInFolder = numberInFolder;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    private String[] mapToString(Address[] array) {
        return InternetAddress.toUnicodeString(array).split(",");
    }

    private Address[] mapToAddress(String[] array) throws AddressException {
        return InternetAddress.parse(String.join(",", array));
    }

}
