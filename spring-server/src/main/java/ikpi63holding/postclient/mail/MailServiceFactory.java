package ikpi63holding.postclient.mail;

import ikpi63holding.postclient.data.maibox.Mailbox;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MailServiceFactory {

    public MailService getService(Mailbox mailbox) throws Exception {
        return new MailService(mailbox);
    }

}
