package au.com.nab.icommerce.customer.service;

import au.com.nab.icommerce.customer.protobuf.CustomerResponse;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import com.google.protobuf.Int32Value;

public interface CustomerService {
    Int32Value createCustomer(PCustomer request);

    CustomerResponse getCustomerById(Int32Value request);
}
