package core.westpac.prime.integration;

import core.westpac.prime.apiobjects.PrimeSummationResponse;
import core.westpac.prime.service.PrimeServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PrimeAPITest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @ParameterizedTest
    @MethodSource("limitProvider")
    @DisplayName("Test spread of summations up to a limit of 10,000,000")
    public void integrationTestHappyDay(int sumToLimit, long expectedResult) {
        RestTemplate restTemplate = restTemplateBuilder.baseUri("http://localhost:8080?upToLimit={upToLimit}").build();

        ResponseEntity<PrimeSummationResponse> response = restTemplate.getForEntity(
                "/v1/prime-summation",
                PrimeSummationResponse.class,
                Map.of("upToLimit", sumToLimit)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getHeader());
        assertEquals(expectedResult, response.getBody().getPayload().getSum());
        assertEquals(sumToLimit, response.getBody().getPayload().getSumToLimit());
    }

    @Test
    @DisplayName("Get sum of primes default limit (10,000,000)")
    public void getSumOfPrimesHappyDay() {
        RestTemplate restTemplate = restTemplateBuilder.baseUri("http://localhost:8080").build();

        ResponseEntity<PrimeSummationResponse> response = restTemplate.getForEntity(
                "/v1/prime-summation",
                PrimeSummationResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getHeader());
        assertEquals(3203324994356L, response.getBody().getPayload().getSum());
        assertEquals(10000000, response.getBody().getPayload().getSumToLimit());
    }

    @Test
    @DisplayName("Handles limit integer which is too big")
    public void integerOverflowHandled() {
        RestTemplate restTemplate = restTemplateBuilder
                .errorHandler(response -> false).baseUri("http://localhost:8080?upToLimit={upToLimit}")
                .build();

        ResponseEntity<PrimeSummationResponse> response = restTemplate.getForEntity(
                "/v1/prime-summation",
                PrimeSummationResponse.class,
                Integer.MAX_VALUE + String.valueOf(ThreadLocalRandom.current().nextInt(1, 10))
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody().getHeader());
        assertEquals("Integer value is too large", response.getBody().getErrorSummary().getMessage());
    }

    public static Stream<Arguments> limitProvider() {
        return PrimeServiceTest.limitProvider();
    }

}