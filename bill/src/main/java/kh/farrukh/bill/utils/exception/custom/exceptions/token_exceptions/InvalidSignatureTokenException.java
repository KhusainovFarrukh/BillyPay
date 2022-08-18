package kh.farrukh.bill.utils.exception.custom.exceptions.token_exceptions;

import kh.farrukh.bill.utils.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.bill.utils.exception.ExceptionMessages.EXCEPTION_TOKEN_INVALID_SIGNATURE;

/**
 * `InvalidSignatureTokenException` is a subclass of `ApiException` that is thrown
 * when signature of token is not valid
 * <p>
 * HttpStatus of the response will be FORBIDDEN
 */
@Getter
public class InvalidSignatureTokenException extends ApiException {

    public InvalidSignatureTokenException() {
        super(
                "Signature of token is not valid",
                HttpStatus.FORBIDDEN,
                EXCEPTION_TOKEN_INVALID_SIGNATURE,
                new Object[]{}
        );
    }
}