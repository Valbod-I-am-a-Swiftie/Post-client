package ikpi63holding.postclient.data.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.data.View;
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

    private static final int DEFAULT_ID = 0;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, insertable = false, nullable = false)
    @JsonView(View.AdminCompact.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView(View.Compact.class)
    private String username;

    @Column(nullable = false)
    @JsonView(View.NewUser.class)
    private String password;

    @JsonView(View.AdminCompact.class)
    private int active = 0;

    @JsonIgnore
    private String roles = "";

    @JsonIgnore
    private String permissions = "";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy
    @JsonManagedReference
    @JsonView(View.User.class)
    private List<Mailbox> mailboxes;

    public void addMailbox(Mailbox mailbox) {
        if (getMailbox(mailbox.getMailLogin()).isPresent()) {
            throw new RuntimeException(mailbox.getMailLogin() + " is already present");
        }
        int maxId = mailboxes.stream().map(Mailbox::getId)
                .reduce(BinaryOperator.maxBy(Integer::compareTo)).orElse(DEFAULT_ID);
        mailbox.setUser(this);
        mailbox.setId(++maxId);
        this.mailboxes.add(mailbox);
    }

    public Optional<Mailbox> getMailbox(int id) {
        return mailboxes.stream().filter(mailbox -> mailbox.getId() == id).findAny();
    }

    public Optional<Mailbox> getMailbox(String mailLogin) {
        return mailboxes.stream().filter(mailbox -> mailbox.getMailLogin().equals(mailLogin))
                .findAny();
    }

    public boolean mailboxExists(String mailLogin) {
        return getMailbox(mailLogin).isPresent();
    }

    @JsonView(View.UserAdmin.class)
    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @JsonView(View.UserAdmin.class)
    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}