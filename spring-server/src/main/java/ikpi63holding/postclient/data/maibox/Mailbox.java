package ikpi63holding.postclient.data.maibox;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.data.View;
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
    @JsonView(View.Compact.class)
    private int id;

    @Column(nullable = false)
    @JsonView(View.Compact.class)
    private String mailLogin;

    @Column(nullable = false)
    @JsonView(View.NewMailbox.class)
    private String mailPassword;

    @Column(nullable = false)
    @JsonView(View.Mailbox.class)
    private String smtpAddr;

    @Column
    @JsonView(View.Mailbox.class)
    private int smtpPort;

    @Column(nullable = false)
    @JsonView(View.Mailbox.class)
    private String imapAddr;

    @Column
    @JsonView(View.Mailbox.class)
    private int imapPort;

    @Column
    @JsonView(View.Mailbox.class)
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