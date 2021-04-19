package au.com.nab.icommerce.customer.auditing.service;

import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesRequest;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesResponse;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import com.google.protobuf.Int32Value;

public interface CustomerActivityService {
    Int32Value addCustomerActivity(PCustomerActivity pCustomerActivity);

    Int32Value addCustomerActivities(PCustomerActivitiesRequest pCustomerActivityListRequest);

    PCustomerActivitiesResponse getCustomerActivitiesByCustomerId(Int32Value customerId);
}
