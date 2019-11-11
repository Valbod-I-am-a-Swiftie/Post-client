package ikpi63holding.postclient.data.maibox;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ikpi63holding.postclient.data.folders.MailFolderSet;
import ikpi63holding.postclient.data.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@IdClass(MailboxKey.class)
public class Mailbox {
    @Id
    @Column(updatable = false, insertable = false, nullable = false)
    private int id;

    @Column(nullable = false)
    private String mailLogin;

    @Column(nullable = false)
    private String mailPassword;

    @Column(nullable = false)
    private String smtpAddr;

    @Column
    private int smtpPort;

    @Column(nullable = false)
    private String imapAddr;

    @Column
    private int imapPort;

    @Column
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String folders = "";

    @Id
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User user;

    public MailFolderSet getFolders() {
        return new MailFolderSet(folders);
    }

    public void setFolders(MailFolderSet folderList) {
        folders = folderList.toString();
    }

}