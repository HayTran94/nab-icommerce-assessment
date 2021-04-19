package au.com.nab.icommerce.common.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BaseClientServicesConfiguration {
    protected final List<ManagedChannel> channels = new ArrayList<>();

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
