package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.protobuf.PCustomerResponse;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceClient {

    @Autowired
    private CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub;

    public PCustomer getCustomerById(Integer customerId) {
        PCustomerResponse response = customerServiceBlockingStub.getCustomerById(Int32Value.of(customerId));
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }

    public int createCustomer(PCustomer customer) {
        Int32Value response = customerServiceBlockingStub.createCustomer(customer);
        return response.getValue();
    }

}
