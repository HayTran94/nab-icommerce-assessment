package au.com.nab.icommerce.common.grpc;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BaseGrpcServicesConfiguration {

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    protected final List<ManagedChannel> channels = new ArrayList<>();

    protected InstanceInfo getGrpcInstanceInfo(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false);
    }

    protected ManagedChannel newChannel(String host, int port) {
        log.info("Bootstrapping GRPC proxy... Host=" + host + "; Port=" + port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        channels.add(channel);
        log.info("GRPC proxy configured");
        return channel;
    }

    @PreDestroy
    public void proxyShutdown() throws InterruptedException {
        log.info("Shutdown of GRPC proxy in progress...");
        for (ManagedChannel channel : channels) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
        log.info("GRPC proxy shutdown");
    }
}
