package core.westpac.prime.controller;

import core.westpac.prime.apiobjects.PrimeSummationResponse;
import core.westpac.prime.service.PrimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
    public ResponseEntity<PrimeSummationResponse> calculatePrimeSummation(@RequestParam(required = false, defaultValue = "10000000") Integer upToLimit) throws Exception {
        log.info(String.format("/v1/prime-summation endpoint called - parameter used: %s", upToLimit));
        return ResponseEntity.ok().body(
                primeService.sumPrimes(upToLimit)
        );
    }

}
