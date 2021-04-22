package au.com.nab.icommerce.api.gateway.config;

import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.common.grpc.BaseGrpcServicesConfiguration;
import au.com.nab.icommerce.customer.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.auditing.api.CustomerAuditingServiceGrpc;
import au.com.nab.icommerce.order.api.OrderServiceGrpc;
import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfiguration {

    @Bean
    public ProductCommandServiceGrpc.ProductCommandServiceBlockingStub productCommandServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("product-command-service");
        return ProductCommandServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public ProductQueryServiceGrpc.ProductQueryServiceBlockingStub productQueryServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("product-query-service");
        return ProductQueryServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub productAuditingServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("product-auditing-service");
        return ProductAuditingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("cart-service");
        return CartServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("order-service");
        return OrderServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("customer-service");
        return CustomerServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public CustomerAuditingServiceGrpc.CustomerAuditingServiceBlockingStub customerAuditingServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo("customer-auditing-service");
        return CustomerAuditingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

}
