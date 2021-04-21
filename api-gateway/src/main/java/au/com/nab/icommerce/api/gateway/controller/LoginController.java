package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.LoginRequest;
import au.com.nab.icommerce.api.gateway.dto.response.LoginResponse;
import au.com.nab.icommerce.api.gateway.security.SecurityProperties;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.protobuf.PSocialInfoRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/customers")
public class LoginController {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private SecurityProperties securityProperties;

    @Value("${security.facebook.clientId}")
    private String facebookClientId;

    @Value("${security.facebook.clientSecret}")
    private String facebookClientSecret;

    @PostMapping("/login")
    public ApiMessage login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            // Verify facebook app data
            if (!facebookClientId.equals(loginRequest.getClientId())
                    || !facebookClientSecret.equals(loginRequest.getClientSecret())) {
                return ApiMessage.INVALID_FACEBOOK_APP_DATA;
            }

            // Get customer by social info
            PSocialInfoRequest socialInfoRequest = PSocialInfoRequest.newBuilder()
                    .setProvider(loginRequest.getProvider())
                    .setProviderId(loginRequest.getProviderId()).build();
            PCustomer customer = customerServiceClient.getCustomerBySocialInfo(socialInfoRequest);
            if (customer == null) {
                customer = PCustomer.newBuilder().build();
            }

            // Set customer info
            customer = customer.toBuilder()
                    .setName(loginRequest.getName())
                    .setEmail(loginRequest.getEmail())
                    .setProvider(loginRequest.getProvider())
                    .setProviderId(loginRequest.getProviderId())
                    .setPhotoUrl(loginRequest.getPhotoUrl())
                    .setToken(loginRequest.getToken()).build();
            int customerId = customerServiceClient.createOrUpdateCustomer(customer);
            if (ErrorCodeHelper.isFail(customerId)) {
                return ApiMessage.LOGIN_FAILED;
            }

            String token = generateToken(String.valueOf(customerId));
            LoginResponse loginResponse = new LoginResponse(customerId, token);
            return ApiMessage.success(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private String generateToken(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + securityProperties.getExpirationTime());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, securityProperties.getSecretKey())
                .compact();
    }

}
