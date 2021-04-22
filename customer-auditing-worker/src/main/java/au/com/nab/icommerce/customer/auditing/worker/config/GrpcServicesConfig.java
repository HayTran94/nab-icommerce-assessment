package au.com.nab.icommerce.customer.auditing.worker.config;

import au.com.nab.icommerce.common.grpc.BaseGrpcServicesConfiguration;
import au.com.nab.icommerce.customer.auditing.api.CustomerAuditingServiceGrpc;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfiguration {

    @Bean
    public CustomerAuditingServiceGrpc.CustomerAuditingServiceBlockingStub customerAuditingServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("customer-auditing-service");
        return CustomerAuditingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

}
