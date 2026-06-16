package core.westpac.prime.apiobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PrimeSummationPayload {

    private int sumToLimit;

    private String description;

    private long sum;

}
