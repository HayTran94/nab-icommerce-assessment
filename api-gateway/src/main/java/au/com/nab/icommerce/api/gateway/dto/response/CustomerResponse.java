package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Integer id;
    private String name;
    private String email;
    private String provider;
    private String providerId;
    private String photoUrl;
}
