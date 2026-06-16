package core.westpac.prime.errorhandling;

import core.westpac.prime.apiobjects.ApiObjectFactory;
import core.westpac.prime.apiobjects.PrimeSummationResponse;
import core.westpac.prime.service.PrimeServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class APIErrorHandling {

    private final ApiObjectFactory apiObjectFactory;

    public APIErrorHandling(ApiObjectFactory apiObjectFactory) {
        this.apiObjectFactory = apiObjectFactory;
    }

    @ExceptionHandler(PrimeServiceException.class)
    public ResponseEntity<PrimeSummationResponse> primeServiceExceptionHandler(PrimeServiceException e) {
        return ResponseEntity.badRequest().body(apiObjectFactory.create(e));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<PrimeSummationResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        if (e.getRequiredType() != null && e.getRequiredType().equals(Integer.class)) {
            if (e.getCause() instanceof NumberFormatException) {
                return ResponseEntity.badRequest().body(apiObjectFactory.create("Integer value is too large"));
            }
        }
        // Default error propagation
        return ResponseEntity.badRequest().body(apiObjectFactory.create(e.getMessage()));
    }

}
