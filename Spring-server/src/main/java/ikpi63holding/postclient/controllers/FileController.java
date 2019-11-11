package ikpi63holding.postclient.controllers;

import ikpi63holding.postclient.data.maibox.Mailbox;
import ikpi63holding.postclient.data.user.User;
import ikpi63holding.postclient.data.user.UserRepository;
import ikpi63holding.postclient.mail.MediaTypeUtils;
import ikpi63holding.postclient.mail.connectors.ImapConnector;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class FileController {

    //private final StorageService storageService;
    @Autowired
    private ServletContext servletContext;

    private final UserRepository userRepository;

    FileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/files/{userName}/{mailboxId}/{folder}/{messageId}/{filename}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveFile(@PathVariable String userName,
            @PathVariable int mailboxId,
            @PathVariable String folder, @PathVariable int messageId, @PathVariable String filename)
            throws Exception {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        Mailbox mailbox = user.getMailbox(mailboxId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No mailbox for " + userName + " with id " + mailboxId));

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
                // Contet-Length
                //.contentLength(file.length()) //
                .body(resource);
    }

}
