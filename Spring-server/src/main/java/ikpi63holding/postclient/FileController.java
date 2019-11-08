package ikpi63holding.postclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.InputStream;

@Controller
public class FileController {

    //private final StorageService storageService;
    @Autowired
    private ServletContext servletContext;

    private final UserRepository repository;

    FileController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/files/{id}/{folder}/{messageId}/{filename}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveFile(@PathVariable long id,@PathVariable String folder,@PathVariable int messageId,@PathVariable String filename) throws Exception {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
        User user = repository.findById(id).orElse(null);
        ImapConnector imapConnector =
                new ImapConnector(user.getMailLogin(), user.getMailPassword(), user.getSmtpAddr());
        InputStream inputStream = imapConnector.getFileFromMessage(folder,messageId,filename);
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
