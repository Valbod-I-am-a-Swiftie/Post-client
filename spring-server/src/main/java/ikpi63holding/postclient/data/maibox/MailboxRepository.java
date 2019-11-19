package ikpi63holding.postclient.data.maibox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, MailboxKey> {

}