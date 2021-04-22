package au.com.nab.icommerce.product.auditing.worker.config;

import au.com.nab.icommerce.common.grpc.BaseGrpcServicesConfiguration;
import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfiguration {

    @Bean
    public ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub productAuditingServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("product-auditing-service");
        return ProductAuditingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

}
