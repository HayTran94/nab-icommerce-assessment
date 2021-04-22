package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerActivityResponse {
    private int customerId;
    private String action;
    private String method;
    private String requestURI;
    private String queryString;
    private long dateTime;
    private String body;
    private String response;
}
