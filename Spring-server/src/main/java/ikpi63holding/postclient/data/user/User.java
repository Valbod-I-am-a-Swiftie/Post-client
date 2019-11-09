package ikpi63holding.postclient.data.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ikpi63holding.postclient.data.maibox.Mailbox;
import java.util.List;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import lombok.Data;

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
    private String username;

    @Column(nullable = false)
    private String password;
 
    private int active = 1;

    private String roles = "";

    private String permissions = "";
  
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Mailbox> mailboxes;

    public void addMailbox(Mailbox mailbox) {
        this.mailboxes.add(mailbox);
        mailbox.setUser(this);
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