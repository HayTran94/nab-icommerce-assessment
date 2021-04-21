package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.customer.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceClient {

    @Autowired
    private CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub;

    public PCustomer getCustomerById(Integer customerId) {
        return customerServiceBlockingStub.getCustomerById(Int32Value.of(customerId));
    }

    public int createCustomer(PCustomer customer) {
        Int32Value res = customerServiceBlockingStub.createCustomer(customer);
        return res.getValue();
    }

}
