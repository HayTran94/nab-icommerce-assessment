package au.com.nab.icommerce.product.auditing.worker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CDCPayload {
    private CDCProduct before;
    private CDCProduct after;
}
