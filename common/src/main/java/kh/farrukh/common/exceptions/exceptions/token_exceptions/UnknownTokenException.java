package kh.farrukh.common.exceptions.exceptions.token_exceptions;

import kh.farrukh.common.exceptions.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.common.exceptions.ExceptionMessages.EXCEPTION_TOKEN_UNKNOWN;

/**
 * `UnknownTokenException` is a subclass of `ApiException` that is thrown
 * when there is unknown problem with token
 * <p>
 * HttpStatus of the response will be FORBIDDEN
 */
@Getter
public class UnknownTokenException extends ApiException {

    public UnknownTokenException() {
        super(
                "Token is invalid",
                HttpStatus.FORBIDDEN,
                EXCEPTION_TOKEN_UNKNOWN,
                new Object[]{}
        );
    }
}
