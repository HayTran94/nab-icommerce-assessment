package au.com.nab.icommerce.customer.auditing.worker.kafka;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.customer.auditing.worker.client.CustomerAuditingServiceClient;
import au.com.nab.icommerce.customer.auditing.worker.dto.CustomerActivityData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerActivityConsumer {

    @Autowired
    private CustomerAuditingServiceClient customerAuditingServiceClient;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "customer-activity")
    public void customerActivityListener(String message) {
        try {
            CustomerActivityData customerActivityData = objectMapper.readValue(message, CustomerActivityData.class);
            String bodyJson = customerActivityData.getBody();
            String responseJson = customerActivityData.getResponse();

            PCustomerActivity pCustomerActivity = PCustomerActivity.newBuilder()
                    .setCustomerId(customerActivityData.getCustomerId())
                    .setAction(customerActivityData.getAction())
                    .setMethod(customerActivityData.getMethod())
                    .setRequestURI(customerActivityData.getRequestURI())
                    .setQueryString(customerActivityData.getQueryString())
                    .setDateTime(customerActivityData.getDateTime())
                    .setBody(bodyJson)
                    .setResponse(responseJson)
                    .build();
            int response = customerAuditingServiceClient.addCustomerActivity(pCustomerActivity);
            if (ErrorCodeHelper.isFail(response)) {
                log.error("FAILED: customerAuditingServiceClient.addCustomerActivity({}) => {}", pCustomerActivity, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
