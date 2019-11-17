package ikpi63holding.postclient;

public class UriDefines {
    public static final String ROOT = "/";
    public static final String ANY_PATH = "/**";
    public static final String ANY_LOCAL = "/*";
    public static final String ANY = ANY_PATH + ANY_LOCAL;
    public static final String REGISTRATION = "/registration";
    public static final String LOGIN = "/login";
    public static final String USER_API = "/api";
    public static final String ADMIN_API = "/admin/api";
    public static final String USER_COLLECTION = "/users";
    public static final String USER_VARIABLE = "user";
    public static final String USER_ENTITY = USER_COLLECTION + "/{" + USER_VARIABLE + "}";
    public static final String MAILBOX_COLLECTION = USER_ENTITY + "/mailboxes";
    public static final String MAILBOX_VARIABLE = "mailbox";
    public static final String MAILBOX_ENTITY = MAILBOX_COLLECTION + "/{" + MAILBOX_VARIABLE + "}";
    public static final String FOLDER_COLLECTION = MAILBOX_ENTITY + "/folders";
    public static final String FOLDER_VARIABLE = "folder";
    public static final String FOLDER_ENTITY = FOLDER_COLLECTION + "/{" + FOLDER_VARIABLE + "}";
    public static final String MESSAGE_COLLECTION = FOLDER_ENTITY + "/messages";
    public static final String MESSAGE_VARIABLE = "message";
    public static final String MESSAGE_ENTITY = MESSAGE_COLLECTION + "/{" + MESSAGE_VARIABLE + "}";
    public static final String FILE_COLLECTION = MESSAGE_ENTITY + "/files";
    public static final String FILE_VARIABLE = "file";
    public static final String FILE_ENTITY = FILE_COLLECTION + "/{" + FILE_VARIABLE + "}";

}
