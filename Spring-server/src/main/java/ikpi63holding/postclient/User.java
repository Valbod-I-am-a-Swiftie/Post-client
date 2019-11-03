package ikpi63holding.postclient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.MapKey;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.ElementCollection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="native"
    )
    private Long id;
    private String login;
    private String password;
    private int folderId = 0;

    @ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="mapKey")
    private Map<Integer, String> folders = new HashMap<>();

    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    private Mailbox mailbox = new Mailbox();

    @ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="mapKey")
    private Map<String, String> mailboxMap = new HashMap<>();

    User() {}

    User(String login, String password, String mailLogin, String mailPassword, String smtpAddr,
            int smtpPort,
            String imapAddr, int imapPort) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String lgn) {
        this.login = lgn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Map.Entry<Integer, String>> getFolders() {
        return this.folders.entrySet();
    }

    public void setDefaultFolders() {
        folders.put(++folderId, "Inbox");
        folders.put(++folderId, "Sent");
        folders.put(++folderId, "Drafts");
        folders.put(++folderId, "Trash");
    }

    public Set<Map.Entry<String, String>> getMailboxMap() {
        return this.mailboxMap.entrySet();
    }

    public void setMailboxMap(String mailLogin, String mailPassword, String smtpAddr, int smtpPort,
            String imapAddr, int imapPort) {
        mailboxMap.put("mailLogin", this.mailbox.getMailLogin());
        mailboxMap.put("mailPassword", this.mailbox.getMailPassword());
        mailboxMap.put("smtpAddr", this.mailbox.getSmtpAddr());
        mailboxMap.put("smtpPort", Integer.toString(this.mailbox.getSmtpPort()));
        mailboxMap.put("imapAddr", this.mailbox.getImapAddr());
        mailboxMap.put("imapPort", Integer.toString(this.mailbox.getImapPort()));
    }

    @JsonIgnore
    public Mailbox getMailbox() {
        return this.mailbox;
    }

    public void setMailbox(String mailLogin, String mailPassword, String smtpAddr, int smtpPort,
            String imapAddr, int imapPort) {
        this.mailbox.setMailLogin(mailLogin);
        this.mailbox.setMailPassword(mailPassword);
        this.mailbox.setSmtpAddr(smtpAddr);
        this.mailbox.setSmtpPort(smtpPort);
        this.mailbox.setImapAddr(imapAddr);
        this.mailbox.setImapPort(imapPort);
    }

    @JsonIgnore
    public String getMailLogin() {
        return this.mailbox.getMailLogin();
    }

    public void setMailLogin(String mailLogin) {
        this.mailbox.setMailLogin(mailLogin);
    }

    @JsonIgnore
    public String getMailPassword() {
        return this.mailbox.getMailPassword();
    }

    public void setMailPassword(String mailPassword) {
        this.mailbox.setMailPassword(mailPassword);
    }

    @JsonIgnore
    public String getSmtpAddr() {
        return this.mailbox.getSmtpAddr();
    }

    public void setSmtpAddr(String smtpAddr) {
        this.mailbox.setSmtpAddr(smtpAddr);
    }

    @JsonIgnore
    public int getSmtpPort() {
        return this.mailbox.getSmtpPort();
    }

    public void setSmtpPort(int smtpPort) {
        this.mailbox.setSmtpPort(smtpPort);
    }

    @JsonIgnore
    public String getImapAddr() {
        return this.mailbox.getImapAddr();
    }

    public void setImapAddr(String imapAddr) {
        this.mailbox.setImapAddr(imapAddr);
    }

    @JsonIgnore
    public int getImapPort() {
        return this.mailbox.getImapPort();
    }

    public void setImapPort(int imapPort) {
        this.mailbox.setImapPort(imapPort);
    }

}