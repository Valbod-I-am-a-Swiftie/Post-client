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

    @ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="mapKey")
    private Map<Integer, String> folders = new HashMap<Integer, String>();

    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    private Mailbox mailbox = new Mailbox();

    User() {}

    User(String login, String password, String mailLogin, String mailPassword, String smtpAddr,
            int smtpPort,
            String imapAddr, int imapPort) {
        this.login = login;
        this.password = password;
        this.mailbox = new Mailbox(mailLogin, mailPassword, smtpAddr, smtpPort, imapAddr, 
                imapPort);
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

    public Set<Map.Entry<Integer, String>> getFolders() {
        return this.folders.entrySet();
    }

    public void setDefaultFolders() {
        folders.put(1, "Inbox");
        folders.put(2, "Sent");
        folders.put(3, "Drafts");
        folders.put(4, "Trash");
    }

    public void setPassword(String password) {
        this.password = password;
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