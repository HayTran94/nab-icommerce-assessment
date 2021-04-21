package au.com.nab.icommerce.customer.service;

import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.protobuf.PCustomerResponse;
import au.com.nab.icommerce.customer.protobuf.PSocialInfoRequest;
import com.google.protobuf.Int32Value;

public interface CustomerService {
    Int32Value createOrUpdateCustomer(PCustomer pCustomer);

    PCustomerResponse getCustomerById(Int32Value id);

    PCustomerResponse getCustomerBySocialInfo(PSocialInfoRequest pSocialInfoRequest);
}
