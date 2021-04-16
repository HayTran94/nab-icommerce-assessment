package au.com.nab.icommerce.customer.service.service;

import au.com.nab.icommerce.customer.service.protobuf.PCustomer;
import com.google.protobuf.Int32Value;

public interface CustomerService {
    Int32Value createCustomer(PCustomer request);

    PCustomer getCustomerById(Int32Value request);
}
