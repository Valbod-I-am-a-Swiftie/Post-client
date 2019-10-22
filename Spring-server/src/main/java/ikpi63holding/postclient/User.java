package ikpi63holding.postclient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class User {
    private @Id @GeneratedValue Long id;
    private String login;
    private String password;
    private String mailLogin;
    private String mailPassword;
    private String smtpAddr;
    private int smtpPort;
    private String imapAddr;
    private int imapPort;

    User() {}

    User(String login, String password, String mailLogin, String mailPassword, String smtpAddr,
            int smtpPort,
            String imapAddr, int imapPort) {
        this.login = login;
        this.password = password;
        this.mailLogin = mailLogin;
        this.mailPassword = mailPassword;
        this.smtpAddr = smtpAddr;
        this.smtpPort = smtpPort;
        this.imapAddr = imapAddr;
        this.imapPort = imapPort;
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

    public String getMailLogin() {
        return this.mailLogin;
    }

    public void setMailLogin(String mailLogin) {
        this.mailLogin = mailLogin;
    }

    public String getMailPassword() {
        return this.mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getSmtpAddr() {
        return this.smtpAddr;
    }

    public void setSmtpAddr(String smtpAddr) {
        this.smtpAddr = smtpAddr;
    }

    public int getSmtpPort() {
        return this.smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getImapAddr() {
        return this.imapAddr;
    }

    public void setImapAddr(String imapAddr) {
        this.imapAddr = imapAddr;
    }

    public int getImapPort() {
        return this.imapPort;
    }

    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

}