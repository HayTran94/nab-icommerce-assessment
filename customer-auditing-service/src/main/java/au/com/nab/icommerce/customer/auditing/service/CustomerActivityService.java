package au.com.nab.icommerce.customer.auditing.service;

import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivityListRequest;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivityListResponse;
import com.google.protobuf.Int32Value;

public interface CustomerActivityService {
    Int32Value addCustomerActivity(PCustomerActivity pCustomerActivity);

    Int32Value addCustomerActivities(PCustomerActivityListRequest pCustomerActivityListRequest);

    PCustomerActivityListResponse getCustomerActivitiesByCustomerId(Int32Value customerId);
}
