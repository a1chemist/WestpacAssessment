package core.westpac.prime.apiobjects;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrimeSummationPayload {

    private int sumToLimit;

    private String description;

    private long sum;

}
