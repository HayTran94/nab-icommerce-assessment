import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistoriesResponse;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Test {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8501)
                .usePlaintext()
                .build();

        ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub blockingStub
                = ProductAuditingServiceGrpc.newBlockingStub(channel);

        PProductPriceHistory productPriceHistory = PProductPriceHistory.newBuilder()
                .setProductId(1)
                .setOldPrice(20000)
                .setNewPrice(18000)
                .setDateTime(System.currentTimeMillis())
                .build();
        Int32Value response = blockingStub.addProductPriceHistory(productPriceHistory);
        System.out.println("addProductPriceHistory: " + response);

        PProductPriceHistoriesResponse response2 = blockingStub.getProductPriceHistories(Int32Value.of(1));
        System.out.println("getProductPriceHistories: " + response2);

        channel.shutdown();
    }
}
