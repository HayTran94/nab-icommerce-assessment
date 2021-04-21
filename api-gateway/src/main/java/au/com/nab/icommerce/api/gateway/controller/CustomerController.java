package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.mapper.response.CustomerResponseMapper;
import au.com.nab.icommerce.api.gateway.security.SecurityHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private CustomerResponseMapper customerResponseMapper;

    @GetMapping("/customer/{customerId}")
    public ApiMessage getCustomerById(@PathVariable Integer customerId) {
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

}
