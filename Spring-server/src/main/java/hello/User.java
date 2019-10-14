package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class User {
	private @Id @GeneratedValue Long id;
	private String login;
	private String mailLogin;
	private String mailPassword;
	private String smtpAddr;
	private int smtpPort;
	private String imapAddr;
	private int imapPort;

	User() {}

	User(String login, String mailLogin, String mailPassword, String smtpAddr, int smtpPort,
			String imapAddr, int imapPort) {
		this.login = login;
		this.mailLogin = mailLogin;
		this.mailPassword = mailPassword;
		this.smtpAddr = smtpAddr;
		this.smtpPort = smtpPort;
		this.imapAddr = imapAddr;
		this.imapPort = imapPort;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setLogin(String lgn) {
		this.login = lgn;
	}

	public String getLogin() {
		return this.login;
	}

	public void setMailLogin(String mailLogin) {
		this.mailLogin = mailLogin;
	}

	public String getMailLogin() {
		return this.mailLogin;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailPassword() {
		return this.mailPassword;
	}

	public void setSmtpAddr(String smtpAddr) {
		this.smtpAddr = smtpAddr;
	}

	public String getSmtpAddr() {
		return this.smtpAddr;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public int getSmtpPort() {
		return this.smtpPort;
	}

	public void setImapAddr(String imapAddr) {
		this.imapAddr = imapAddr;
	}

	public String getImapAddr() {
		return this.imapAddr;
	}

	public void setImapPort(int imapPort) {
		this.imapPort = imapPort;
	}

	public int getImapPort() {
		return this.imapPort;
	}
}