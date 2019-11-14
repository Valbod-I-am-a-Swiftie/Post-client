package ikpi63holding.postclient.data;

public class View {

    // compact is used to mark properties showed in lists
    public interface Compact {}

    // following views are used to mark properties shown in single object
    public interface User extends Compact {}

    public interface Mailbox extends Compact {}

    public interface Folder extends Compact {}

    // following views are used to mark private info to be available only when adding new entities
    public interface NewUser extends User {}

    public interface NewMailbox extends Mailbox {}

    public interface NewFolder extends Folder {}

    // following views mark properties to be shown only in according admin mode
    public interface AdminCompact extends Compact {}

    public interface UserAdmin extends User, AdminCompact {}

    public interface MailboxAdmin extends Mailbox, AdminCompact {}

    public interface FolderAdmin extends Folder, AdminCompact {}

    public interface NewUserAdmin extends NewUser, UserAdmin {}

    public interface NewMailboxAdmin extends NewMailbox, MailboxAdmin {}

    public interface NewFolderAdmin extends NewFolder, FolderAdmin {}

}
