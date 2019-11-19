package ikpi63holding.postclient.data.maibox;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class MailboxKey implements Serializable {
    UUID user;
    int id;

}
