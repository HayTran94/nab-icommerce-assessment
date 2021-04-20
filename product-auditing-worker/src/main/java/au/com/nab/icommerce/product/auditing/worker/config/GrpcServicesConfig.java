package au.com.nab.icommerce.product.auditing.worker.config;

import au.com.nab.icommerce.common.grpc.BaseGrpcServicesConfiguration;
import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfiguration {

    @Value("${product-auditing-service.grpcserver.host}")
    private String productAuditingServiceGrpcServerHost;

    @Value("${product-auditing-service.grpcserver.port}")
    private int productAuditingServiceGrpcServerPort;

    @Bean
    public ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub productAuditingServiceBlockingStub() {
        return ProductAuditingServiceGrpc.newBlockingStub(newChannel(productAuditingServiceGrpcServerHost, productAuditingServiceGrpcServerPort));
    }
}
