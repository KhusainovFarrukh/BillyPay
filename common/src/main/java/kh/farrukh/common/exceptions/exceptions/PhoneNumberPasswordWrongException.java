package kh.farrukh.common.exceptions.exceptions;

import kh.farrukh.common.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static kh.farrukh.common.exceptions.ExceptionMessages.EXCEPTION_PHONE_NUMBER_PASSWORD_WRONG;

/**
 * `EmailPasswordInvalidException` is a subclass of `ApiException` that is thrown
 * when email or password provided in login request is invalid
 * <p>
 * HttpStatus of the response will be UNAUTHORIZED
 */
@Getter
public class PhoneNumberPasswordWrongException extends ApiException {

    private final Type errorType;

    public PhoneNumberPasswordWrongException(Type errorType) {
        super(
            String.format("%s is wrong", errorType.errorName),
            HttpStatus.UNAUTHORIZED,
            EXCEPTION_PHONE_NUMBER_PASSWORD_WRONG,
            new Object[]{errorType.errorName}
        );
        this.errorType = errorType;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum Type {
        PHONE_NUMBER("Phone number"),
        PASSWORD("Password");

        private String errorName;
    }
}
