package au.com.nab.icommerce.product.auditing.worker.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDCMessage {
    private CDCPayload payload;
}
