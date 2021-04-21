package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    String name;
    String email;
    String provider;
    String providerId;
    String photoUrl;
    String token;
    String clientId;
    String clientSecret;
}
