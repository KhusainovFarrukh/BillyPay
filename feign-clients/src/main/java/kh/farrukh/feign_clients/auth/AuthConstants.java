package kh.farrukh.feign_clients.auth;

public class AuthConstants {

    public static final String ENDPOINT_PREFIX_AUTH = "/api/v1";
    public static final String ENDPOINT_POSTFIX_REGISTER = "/register";
    public static final String ENDPOINT_POSTFIX_LOGIN = "/login";
    public static final String ENDPOINT_LOGIN = ENDPOINT_PREFIX_AUTH + ENDPOINT_POSTFIX_LOGIN;
    public static final String ENDPOINT_REGISTER = ENDPOINT_PREFIX_AUTH + ENDPOINT_POSTFIX_REGISTER;
}
