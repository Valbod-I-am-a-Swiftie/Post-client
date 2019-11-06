package ikpi63holding.postclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MailFolder {

    enum FolderType{
        INBOX, SENT, DRAFTS, TRASH, NONE
    }

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private FolderType folderType = FolderType.NONE;

    public MailFolder(String notation){
        var parts = notation.split(":");
        switch (parts.length){
            case 2:
                folderType = FolderType.valueOf(parts[1]);
                /*FALL THROUGH*/
            case 1:
                name = parts[0];
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString(){
        return name + (folderType != FolderType.NONE ? ":" + folderType.toString() : "");
    }

    static List<MailFolder> parseToList(String listString){
        return Arrays.stream(listString.split(", ")).map(MailFolder::new).collect(
                Collectors.toCollection(ArrayList::new));
    }

    static String parseToString(List<MailFolder> list){
        return list.stream().map(MailFolder::toString).collect(Collectors.joining(", "));
    }

    static List<MailFolder> defaultFolders(){
        return new ArrayList<>(List.of(
                new MailFolder("INBOX", FolderType.INBOX),
                new MailFolder("Sent", FolderType.SENT),
                new MailFolder("Drafts", FolderType.DRAFTS),
                new MailFolder("Trash", FolderType.TRASH)
        ));
    }

    static String appendFolder(String folders, MailFolder folder){
        return folders + ", " + folder;
    }

}
