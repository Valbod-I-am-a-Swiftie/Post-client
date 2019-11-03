package ikpi63holding.postclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String mailLogin;
    private String mailPassword;
    private String smtpAddr;
    private int smtpPort;
    private String imapAddr;
    private int imapPort;

    private int active;

    private String roles = "";

    private String permissions = "";

    User() {}

    User(String username, String password, String mailLogin, String mailPassword,
            String smtpAddr,int smtpPort,
            String imapAddr, int imapPort,
            String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.mailLogin = mailLogin;
        this.mailPassword = mailPassword;
        this.smtpAddr = smtpAddr;
        this.smtpPort = smtpPort;
        this.imapAddr = imapAddr;
        this.imapPort = imapPort;

        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    // New methods for auth
    public int getActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}