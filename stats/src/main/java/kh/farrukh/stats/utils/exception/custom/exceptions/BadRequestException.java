package kh.farrukh.stats.utils.exception.custom.exceptions;

import kh.farrukh.stats.utils.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.stats.utils.exception.ExceptionMessages.EXCEPTION_BAD_REQUEST;

/**
 * `BadRequestException` is a subclass of `ApiException` that is thrown
 * when a required value is not valid
 * <p>
 * HttpStatus of the response will be BAD_REQUEST
 */
@Getter
public class BadRequestException extends ApiException {

    private final String invalidValue;

    public BadRequestException(String invalidValue) {
        super(
                String.format("%s is not valid", invalidValue),
                HttpStatus.BAD_REQUEST,
                EXCEPTION_BAD_REQUEST,
                new Object[]{invalidValue}
        );
        this.invalidValue = invalidValue;
    }
}
