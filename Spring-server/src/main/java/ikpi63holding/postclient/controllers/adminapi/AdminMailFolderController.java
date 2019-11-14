package ikpi63holding.postclient.controllers.adminapi;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.controllers.abstractapi.AbstractMailFolderController;
import ikpi63holding.postclient.data.View;
import ikpi63holding.postclient.data.folders.MailFolderSet;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/users")
public class AdminMailFolderController extends AbstractMailFolderController {

    @Autowired
    protected AdminMailFolderController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    @Override
    @GetMapping("/{username}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.AdminCompact.class)
    public MailFolderSet getFolders(@PathVariable String username, @PathVariable int mailboxId) {
        return super.getFolders(username, mailboxId);
    }

    @Override
    @PutMapping("/{username}/mailboxes/{mailboxId}/folders")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.NewFolderAdmin.class)
    public MailFolderSet addFolders(@RequestBody MailFolderSet newMailFolderSet,
            @PathVariable String username, @PathVariable int mailboxId)
            throws Exception {
        return super.addFolders(newMailFolderSet, username, mailboxId);
    }

}
