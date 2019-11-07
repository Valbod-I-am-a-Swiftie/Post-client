package ikpi63holding.postclient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Mailbox {
	@Id 
    @GeneratedValue(
        strategy= GenerationType.AUTO
    )
    private Long id;

    @Column(nullable=false)
    private String mailLogin;

    @Column(nullable=false)
    private String mailPassword;

    @Column(nullable=false)
    private String smtpAddr;

    @Column
    private int smtpPort;

    @Column(nullable=false)
    private String imapAddr;

    @Column
    private int imapPort;

    @Column
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String folders;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User user;

    Mailbox() {
        folders = MailFolder.parseToString(MailFolder.defaultFolders());
    }

    public List<MailFolder> getFolders(){
        return MailFolder.parseToList(folders);
    }

    public void setFolders(List<MailFolder> folderList){
        folders = MailFolder.parseToString(folderList);
    }

    public void addFolder(MailFolder folder){
        folders = MailFolder.appendFolder(folders, folder);
    }


}