package core.westpac.prime.apiobjects;

import core.westpac.prime.service.PrimeServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ApiObjectFactory {

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

    public PrimeSummationResponse create(PrimeServiceException e) {
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
                                .message(e.getMessage())
                                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                                .build()
                )
                .build();
    }

}
