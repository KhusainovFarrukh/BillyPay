package kh.farrukh.feign_clients.user;

public class UserConstants {

    public static final String ENDPOINT_USER = "/api/v1/users";
    public static final String ENDPOINT_POSTFIX_ID = "{id}";
    public static final String ENDPOINT_POSTFIX_USER_ROLE = ENDPOINT_POSTFIX_ID + "/role";
    public static final String ENDPOINT_POSTFIX_USER_PASSWORD = ENDPOINT_POSTFIX_ID + "/password";

    public static final String PARAM_ID = "id";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "page_size";
}
