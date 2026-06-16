package core.westpac.prime.controller;

import core.westpac.prime.apiobjects.PrimeSummationResponse;
import core.westpac.prime.service.PrimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PrimeControllerTest {

    @Mock
    private PrimeService primeService;

    @InjectMocks
    private PrimeController primeController;

    @Test
    @DisplayName("Prime Controller HappyDay")
    public void primeControllerHappyDay() throws Exception {

        PrimeSummationResponse expected = mock(PrimeSummationResponse.class);

        // given
        int upToLimit =  ThreadLocalRandom.current().nextInt(1, PrimeService.SUMMATION_LIMIT);
        given(primeService.sumPrimes(upToLimit)).willReturn(expected);

        // when
        ResponseEntity<PrimeSummationResponse> actual = primeController.calculatePrimeSummation(upToLimit);

        // then
        assertEquals(expected, actual.getBody());
    }

}