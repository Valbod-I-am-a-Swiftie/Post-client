package ikpi63holding.postclient;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MailServiceFactory {

    MailService getService(Mailbox mailbox) throws Exception {
        return new MailService(mailbox);
    }

}
