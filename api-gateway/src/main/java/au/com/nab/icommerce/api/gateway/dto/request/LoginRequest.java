package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    String name;

    @NotBlank
    String email;

    @NotBlank
    String provider;

    @NotBlank
    String providerId;

    String photoUrl;

    @NotBlank
    String token;

    @NotBlank
    String clientId;

    @NotBlank
    String clientSecret;
}
