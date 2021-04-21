package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.LoginRequest;
import au.com.nab.icommerce.api.gateway.mapper.response.CustomerResponseMapper;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.protobuf.PSocialInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private CustomerResponseMapper customerResponseMapper;

    @Value("${security.facebook.clientId}")
    private String facebookClientId;

    @Value("${security.facebook.clientSecret}")
    private String facebookClientSecret;

    @PostMapping
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
            int response = customerServiceClient.createOrUpdateCustomer(customer);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.LOGIN_FAILED;
            }

            return ApiMessage.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/customer/{customerId}")
    public ApiMessage getCustomerById(@PathVariable Integer customerId) {
        try {
            PCustomer customer = customerServiceClient.getCustomerById(customerId);
            if (customer == null) {
                return ApiMessage.CUSTOMER_NOT_FOUND;
            }

            return ApiMessage.success(customerResponseMapper.toDomain(customer));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
