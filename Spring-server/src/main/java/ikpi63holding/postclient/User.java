package ikpi63holding.postclient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.ElementCollection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="native"
    )
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Mailbox> mailboxes;

    public void addMailbox(Mailbox mailbox) {
        this.mailboxes.add(mailbox);
        mailbox.setUser(this);
    }

}