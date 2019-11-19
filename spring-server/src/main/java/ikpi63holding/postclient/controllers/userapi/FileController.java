package ikpi63holding.postclient.controllers.userapi;

import ikpi63holding.postclient.controllers.abstractapi.AbstractMailServiceController;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MailServiceFactory;
import ikpi63holding.postclient.mail.MediaTypeUtils;
import ikpi63holding.postclient.mail.connectors.ImapConnector;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class FileController extends AbstractMailServiceController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    protected FileController(UserRepository userRepository,
            MailServiceFactory mailServiceFactory) {
        super(userRepository, mailServiceFactory);
    }

    @GetMapping("/{username}/mailboxes/{mailboxId}/"
            + "folders/{folder}/messages/{messageId}/files/{filename}")
    @ResponseBody
    protected ResponseEntity<InputStreamResource> serveFile(@PathVariable String username,
            @PathVariable int mailboxId,
            @PathVariable String folder, @PathVariable int messageId, @PathVariable String filename)
            throws Exception {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
        var mailbox = getMailbox(username, mailboxId);

        ImapConnector imapConnector =
                new ImapConnector(mailbox.getMailLogin(), mailbox.getMailPassword(),
                        mailbox.getImapAddr());
        InputStream inputStream = imapConnector.getFileFromMessage(folder, messageId, filename);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                // Content-Type
                .contentType(mediaType)
                // TODO Content-Length
                //.contentLength(file.length()) //
                .body(resource);
    }

}
