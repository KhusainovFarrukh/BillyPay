package kh.farrukh.bill.utils.exception.custom.exceptions.token_exceptions;

import kh.farrukh.bill.utils.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.bill.utils.exception.ExceptionMessages.EXCEPTION_TOKEN_EXPIRED;

/**
 * `ExpiredTokenException` is a subclass of `ApiException` that is thrown
 * when expired token is used
 * <p>
 * HttpStatus of the response will be FORBIDDEN
 */
@Getter
public class ExpiredTokenException extends ApiException {

    public ExpiredTokenException() {
        super(
                "Token is expired",
                HttpStatus.FORBIDDEN,
                EXCEPTION_TOKEN_EXPIRED,
                new Object[]{}
        );
    }
}
