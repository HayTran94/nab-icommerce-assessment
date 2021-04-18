package au.com.nab.icommerce.customer.service;

import au.com.nab.icommerce.customer.protobuf.PCustomer;
import com.google.protobuf.Int32Value;

public interface CustomerService {
    Int32Value createCustomer(PCustomer pCustomer);

    PCustomer getCustomerById(Int32Value id);
}
