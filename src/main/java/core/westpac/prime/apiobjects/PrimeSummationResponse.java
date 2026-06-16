package core.westpac.prime.apiobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrimeSummationResponse {

    private Header header;

    private PrimeSummationPayload payload;

    private ErrorSummary errorSummary;

}
