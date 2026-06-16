package core.westpac.prime.service;

import core.westpac.prime.apiobjects.ApiObjectFactory;
import core.westpac.prime.apiobjects.PrimeSummationResponse;
import org.springframework.stereotype.Service;

@Service
public class PrimeService {

    public static final int SUMMATION_LIMIT = 10000000;

    private final ApiObjectFactory apiObjectFactory;

    public PrimeService(ApiObjectFactory apiObjectFactory) {
        this.apiObjectFactory = apiObjectFactory;
    }

    public PrimeSummationResponse sumPrimes() throws PrimeServiceException {
        return sumPrimes(null);
    }

    /**
     * The Sieve of Eratosthenes algorithm.
     * Given a limit of primes up to a certain number remove all the non-primes (composites)
     * firs then sum the remaining prime numbers
     * @return PrimeSummationResponse
     */
    public PrimeSummationResponse sumPrimes(Integer upToLimit) throws PrimeServiceException {
        if (upToLimit == null) {
            upToLimit = SUMMATION_LIMIT;
        }
        if (upToLimit > SUMMATION_LIMIT) {
            throw new PrimeServiceException(
                    String.format("Maximum prime summation limit exceeded (max value: %s)", SUMMATION_LIMIT)
            );
        }

        // Default values are false (is prime until consider not)
        boolean[] isComposite = new boolean[SUMMATION_LIMIT + 1];
        isComposite[0] = true;
        isComposite[1] = true;

        // Main algorithm - Sieve of Eratosthenes
        int sqrtLimit = (int) Math.sqrt(SUMMATION_LIMIT);
        for (int p = 2; p <= sqrtLimit; p++) {
            if (!isComposite[p]) {
                for (int i = p * p; i <= SUMMATION_LIMIT; i += p) {
                    isComposite[i] = true;
                }
            }
        }

        // Calculate the sum ignoring composite numbers (not prime)
        long sum = 0;
        for (int i = 2; i <= SUMMATION_LIMIT; i++) {
            if (!isComposite[i]) {
                sum += i;
            }
        }

        // Package the result ready for API use
        return apiObjectFactory.create(
                SUMMATION_LIMIT,
                "The sum of primes up to " + SUMMATION_LIMIT + " is: " + sum,
                sum
        );
    }

}
