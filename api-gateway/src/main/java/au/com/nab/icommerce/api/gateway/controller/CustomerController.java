package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.aspect.CustomerActivity;
import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.response.CustomerActivityResponse;
import au.com.nab.icommerce.api.gateway.mapper.response.CustomerActivityResponseMapper;
import au.com.nab.icommerce.api.gateway.mapper.response.CustomerResponseMapper;
import au.com.nab.icommerce.api.gateway.security.SecurityHelper;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    private final CustomerResponseMapper customerResponseMapper = CustomerResponseMapper.INSTANCE;

    private final CustomerActivityResponseMapper customerActivityResponseMapper = CustomerActivityResponseMapper.INSTANCE;

    @GetMapping("/{customerId}")
    @CustomerActivity("GET_CUSTOMER_INFO")
    public ApiMessage getCustomerInfo(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            return ApiMessage.success(customerResponseMapper.toDomain(customer));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{customerId}/activities")
    public ApiMessage getCustomerActivities(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            List<PCustomerActivity> pCustomerActivities =
                    customerServiceClient.getCustomerActivitiesByCustomerId(customerId);
            List<CustomerActivityResponse> customerActivityResponses =
                    customerActivityResponseMapper.toDomainList(pCustomerActivities);
            return ApiMessage.success(customerActivityResponses);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
