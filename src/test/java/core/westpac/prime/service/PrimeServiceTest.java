package core.westpac.prime.service;

import core.westpac.prime.apiobjects.ApiObjectFactory;
import core.westpac.prime.apiobjects.PrimeSummationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PrimeServiceTest {

    @Mock
    private ApiObjectFactory apiObjectFactory;

    @InjectMocks
    private PrimeService primeService;

    @Test
    @DisplayName("Default summation calculated correctly")
    void defaultSummationHappyDay() throws Exception {

        PrimeSummationResponse expected = mock(PrimeSummationResponse.class);

        // given
        given(
                apiObjectFactory.create(
                        PrimeService.SUMMATION_LIMIT,
                        "The sum of primes up to 10000000 is: 3203324994356",
                        3203324994356L
                )
        ).willReturn(expected);

        // when
        PrimeSummationResponse actual = primeService.sumPrimes();

        // Then
        assertEquals(expected, actual);
    }

}