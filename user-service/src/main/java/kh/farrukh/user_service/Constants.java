package kh.farrukh.user_service;

public class Constants {

    public static final String ENDPOINT_USER = "/api/v1/users";
    public static final String ENDPOINT_USER_ROLE = ENDPOINT_USER + "/{id}/role";
    public static final String SEQUENCE_NAME_USER_ID = "app_user_id_seq";
    public static final String TABLE_NAME_USER = "app_user";
    public static final String GENERATOR_NAME_USER_ID = "user_id_generator";
}
