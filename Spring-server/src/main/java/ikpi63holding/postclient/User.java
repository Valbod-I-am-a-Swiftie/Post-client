package ikpi63holding.postclient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private String username;

    @Column(nullable = false)
    private String password;
 
    private int active;

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