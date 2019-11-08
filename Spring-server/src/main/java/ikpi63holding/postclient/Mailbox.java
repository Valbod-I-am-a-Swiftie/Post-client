package ikpi63holding.postclient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Mailbox {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

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