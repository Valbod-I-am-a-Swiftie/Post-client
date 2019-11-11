package ikpi63holding.postclient.data.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ikpi63holding.postclient.data.maibox.Mailbox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BinaryOperator;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, insertable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active = 0;

    @JsonIgnore
    private String roles = "";

    @JsonIgnore
    private String permissions = "";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy
    @JsonManagedReference
    private List<Mailbox> mailboxes;

    public void addMailbox(Mailbox mailbox) {
        // TODO: later check for unique emailLogin here, and maybe throw otherwise
        //  @Max
        int maxId = mailboxes.stream().map(Mailbox::getId)
                .reduce(BinaryOperator.maxBy(Integer::compareTo)).orElse(0);
        mailbox.setUser(this);
        mailbox.setId(++maxId);
        this.mailboxes.add(mailbox);
    }

    public Optional<Mailbox> getMailbox(int id){
        return mailboxes.stream().filter(mailbox -> mailbox.getId() == id).findAny();
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}