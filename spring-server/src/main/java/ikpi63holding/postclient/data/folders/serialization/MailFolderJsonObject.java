package ikpi63holding.postclient.data.folders.serialization;

import com.fasterxml.jackson.annotation.JsonView;
import ikpi63holding.postclient.data.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailFolderJsonObject {

    @JsonView(View.Compact.class)
    String name;

    @JsonView(View.Folder.class)
    String type;

}
