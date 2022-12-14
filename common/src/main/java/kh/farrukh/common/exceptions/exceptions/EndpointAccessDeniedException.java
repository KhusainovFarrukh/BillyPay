package kh.farrukh.common.exceptions.exceptions;

import kh.farrukh.common.exceptions.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.common.exceptions.ExceptionMessages.EXCEPTION_ENDPOINT_ACCESS_DENIED;

/**
 * `EndpointAccessDeniedException` is a subclass of `ApiException` that is thrown
 * when a user doesn't have enough permission/role to access the endpoint
 * <p>
 * HttpStatus of the response will be FORBIDDEN
 */
@Getter
public class EndpointAccessDeniedException extends ApiException {

    private final String endpoint;

    public EndpointAccessDeniedException(String endpoint) {
        super(
                String.format("You don't have access to %s", endpoint),
                HttpStatus.FORBIDDEN,
                EXCEPTION_ENDPOINT_ACCESS_DENIED,
                new Object[]{endpoint}
        );
        this.endpoint = endpoint;
    }
}
