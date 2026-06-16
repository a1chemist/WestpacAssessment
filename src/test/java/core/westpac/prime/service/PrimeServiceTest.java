package core.westpac.prime.service;

import core.westpac.prime.apiobjects.ApiObjectFactory;
import core.westpac.prime.apiobjects.PrimeSummationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PrimeServiceTest {

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

    @Test
    @DisplayName("Null limit defaults and calculates summation correctly")
    void nullLimitHandled() throws Exception {

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
        PrimeSummationResponse actual = primeService.sumPrimes(null);

        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("limitProvider")
    @DisplayName("Summation calculated correctly for spread of limits")
    void summationHappyDay(int sumToLimit, long expectedResult) throws Exception {

        PrimeSummationResponse expected = mock(PrimeSummationResponse.class);

        // given
        given(
                apiObjectFactory.create(
                        sumToLimit,
                        String.format("The sum of primes up to %s is: %s", sumToLimit, expectedResult),
                        expectedResult
                )
        ).willReturn(expected);

        // when
        PrimeSummationResponse actual = primeService.sumPrimes(sumToLimit);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Summation calculated correctly for 100")
    void summationFor100() throws Exception {

        PrimeSummationResponse expected = mock(PrimeSummationResponse.class);
        int sumToLimit = 100;
        long result = 1060L;

        // given
        given(
                apiObjectFactory.create(
                        sumToLimit,
                        String.format("The sum of primes up to %s is: %s", sumToLimit, result),
                        result
                )
        ).willReturn(expected);

        // when
        PrimeSummationResponse actual = primeService.sumPrimes(sumToLimit);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Summation limit exceeded handled correctly")
    void summationLimitExceededHandledCorrectly() throws Exception {

        int someExceededLimit = PrimeService.SUMMATION_LIMIT + ThreadLocalRandom.current().nextInt(1, 10);

        // when
        PrimeServiceException actual = assertThrows(PrimeServiceException.class, () -> primeService.sumPrimes(someExceededLimit));

        // Then
        assertEquals("Maximum prime summation limit exceeded (max value: 10000000)", actual.getMessage());
    }

    @Test
    @DisplayName("Summation limit handles 0 correctly")
    void summationLimitHandlesZeroCorrectly() throws Exception {

        // when
        PrimeServiceException actual = assertThrows(PrimeServiceException.class, () -> primeService.sumPrimes(0));

        // Then
        assertEquals("Prime summation limit must be greater than 0", actual.getMessage());
    }

    @Test
    @DisplayName("Summation limit handles negatives correctly")
    void summationLimitHandlesNegativeCorrectly() throws Exception {

        int someNegativeLimit = -ThreadLocalRandom.current().nextInt(1, 10);

        // when
        PrimeServiceException actual = assertThrows(PrimeServiceException.class, () -> primeService.sumPrimes(someNegativeLimit));

        // Then
        assertEquals("Prime summation limit must be greater than 0", actual.getMessage());
    }

    public static Stream<Arguments> limitProvider() {
        return Stream.of(
                Arguments.of(10, 17L),
                Arguments.of(100, 1060L),
                Arguments.of(1000, 76127L),
                Arguments.of(10000, 5736396L),
                Arguments.of(100000, 454396537L),
                Arguments.of(1000000, 37550402023L),
                Arguments.of(10000000, 3203324994356L)
        );
    }
}