package core.westpac.prime.apiobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class PrimeSummationResponse {

    private Header header;

    private PrimeSummationPayload payload;

    private ErrorSummary errorSummary;

}
