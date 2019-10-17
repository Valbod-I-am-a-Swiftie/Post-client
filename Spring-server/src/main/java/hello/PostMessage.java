package hello;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

public class PostMessage {
    private String title;
    private String content;
    private String contentType;
    private Date date;
    private Address[] addresses;

    public PostMessage(String title, String content, String contentType, Date date, Address[] addresses) {
        this.title = title;
        this.content = content;
        this.contentType = contentType;
        this.date = date;
        this.addresses = addresses;
    }

    public PostMessage(Message message) throws MessagingException, IOException {
        this.title = message.getSubject();
        this.content = message.getContent().toString();
        this.contentType = message.getContentType();
        this.date = message.getSentDate();
        this.addresses = message.getFrom();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public Address[] getAddresses() {
        return addresses;
    }

    public void setAddresses(Address[] addresses) {
        this.addresses = addresses;
    }
}
