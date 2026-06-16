package core.westpac.prime.controller;

import core.westpac.prime.apiobjects.PrimeSummationResponse;
import core.westpac.prime.service.PrimeService;
import core.westpac.prime.service.PrimeServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Prime Number Summation")
@RestController
public class PrimeController {

    private final PrimeService primeService;

    public PrimeController(PrimeService primeService) {
        this.primeService = primeService;
    }

    @Operation(
            summary = "Sum the all prime numbers to 10,000,000"
    )
    @GetMapping("/v1/prime-summation")
    public ResponseEntity<PrimeSummationResponse> calculatePrimeSummation() throws Exception {
        return ResponseEntity.ok().body(
                primeService.sumPrimes()
        );
    }

}
