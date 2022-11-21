package kh.farrukh.user_service.role;

import kh.farrukh.common.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class DefaultRoleDeletionException extends ApiException {

    public final static String EXCEPTION_DEFAULT_ROLE_DELETION = "message.exception.default_role_deletion";

    public DefaultRoleDeletionException() {
        super(
                "Default role cannot be deleted",
                HttpStatus.BAD_REQUEST,
                EXCEPTION_DEFAULT_ROLE_DELETION,
                null
        );
    }
}
