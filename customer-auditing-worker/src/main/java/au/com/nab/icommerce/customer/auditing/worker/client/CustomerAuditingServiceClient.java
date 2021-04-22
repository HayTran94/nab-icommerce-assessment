package au.com.nab.icommerce.customer.auditing.worker.client;

import au.com.nab.icommerce.customer.auditing.api.CustomerAuditingServiceGrpc;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerAuditingServiceClient {

    @Autowired
    private CustomerAuditingServiceGrpc.CustomerAuditingServiceBlockingStub customerAuditingServiceBlockingStub;

    public int addCustomerActivity(PCustomerActivity pCustomerActivity) {
        Int32Value response = customerAuditingServiceBlockingStub.addCustomerActivity(pCustomerActivity);
        return response.getValue();
    }
}
