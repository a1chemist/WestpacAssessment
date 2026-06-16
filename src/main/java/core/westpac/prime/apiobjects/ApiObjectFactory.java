package core.westpac.prime.apiobjects;

import core.westpac.prime.service.PrimeServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * Basic object factory for the API layer which leverages lombok object builders.
 * This class hides bloated object creation code.
 */
@Component
public class ApiObjectFactory {

    /**
     * This 'create' method is used for creating successful API response payloads
     * @param sumToLimit
     * @param description
     * @param sum
     * @return
     */
    public PrimeSummationResponse create(int sumToLimit, String description, long sum) {
        return PrimeSummationResponse
                .builder()
                .header(
                        Header
                                .builder()
                                .timestamp(LocalTime.now().toString())
                                .build()
                )
                .payload(
                        PrimeSummationPayload
                                .builder()
                                .sumToLimit(sumToLimit)
                                .description(description)
                                .sum(sum)
                                .build()
                )
                .build();
    }

    /**
     * In the event of a service layer exception we can gracefully generate
     * a response payload via a typed exception: PrimeServiceException
     * @param e
     * @return
     */
    public PrimeSummationResponse create(PrimeServiceException e) {
        return create(e.getMessage());
    }

    /**
     * In the event of a service layer exception we can gracefully generate
     * a response payload via a string message
     * @param message
     * @return
     */
    public PrimeSummationResponse create(String message) {
        return PrimeSummationResponse
                .builder()
                .header(
                        Header
                                .builder()
                                .timestamp(LocalTime.now().toString())
                                .build()
                )
                .errorSummary(
                        ErrorSummary
                                .builder()
                                .message(message)
                                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                                .build()
                )
                .build();
    }

}
