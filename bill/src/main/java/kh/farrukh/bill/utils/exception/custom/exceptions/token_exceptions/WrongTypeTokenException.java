package kh.farrukh.bill.utils.exception.custom.exceptions.token_exceptions;

import kh.farrukh.bill.utils.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.bill.utils.exception.ExceptionMessages.EXCEPTION_TOKEN_WRONG_TYPE;

/**
 * `WrongTypeTokenException` is a subclass of `ApiException` that is thrown
 * when access token is used in place of refresh token or vice-versa
 * <p>
 * HttpStatus of the response will be FORBIDDEN
 */
@Getter
public class WrongTypeTokenException extends ApiException {

    public WrongTypeTokenException() {
        super(
                "Access token and refresh token can not be used in place of each other",
                HttpStatus.FORBIDDEN,
                EXCEPTION_TOKEN_WRONG_TYPE,
                new Object[]{}
        );
    }
}
