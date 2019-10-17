package hello;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostMessage {
    private int numberInFolder;
    private String title;
    private Object content;
    private String contentType;
    private Date date;
    private Address[] recivers;
    private Address[] recipients;
    private String folder;




    public PostMessage(String title, String content, String contentType, Date date, Address[] recivers, Address[] recipients, Folder folder) {
        this.title = title;
        this.content = content;
        this.contentType = contentType;
        this.date = date;
        this.recivers = recivers;
        this.recipients = recipients;
        this.folder = folder.getFullName();
    }
    public PostMessage(Message message) throws MessagingException, IOException {
        this.title = message.getSubject();
        this.content = message.getContent();
        this.contentType = message.getContentType();
        this.numberInFolder = message.getMessageNumber();
        this.date = message.getSentDate();
        this.recivers = message.getFrom();
        this.recipients = message.getAllRecipients();
        this.folder = message.getFolder().getFullName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getContent() {

        if (this.contentType.contains("text") || this.contentType.contains("html"))
        {
            return content.toString();
        }
        if (this.contentType.contains("multipart"))
        {
            List<Object> ret = new ArrayList<>();
            MimeMultipart mimeMultipart = (MimeMultipart) content;
            BodyPart bodyPart;
            int count = 0;
            try {
                count = mimeMultipart.getCount();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            for (int i = 0; i<count; i++)
            {
                try {
                    bodyPart = mimeMultipart.getBodyPart(i);
                    ret.add(bodyPart.getContent());
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    public Address[] getRecivers() {
        return recivers;
    }

    public void setRecivers(Address[] addresses) {
        this.recivers = addresses;
    }

    public int getNumberInFolder() {
        return numberInFolder;
    }

    public void setNumberInFolder(int numberInFolder) {
        this.numberInFolder = numberInFolder;
    }


    public Address[] getRecipients() {
        return recipients;
    }

    public void setRecipients(Address[] recipients) {
        this.recipients = recipients;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
