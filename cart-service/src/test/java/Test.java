import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.cart.protobuf.PAddItemRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.cart.protobuf.PItem;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Test {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8301)
                .usePlaintext()
                .build();

        CartServiceGrpc.CartServiceBlockingStub blockingStub = CartServiceGrpc.newBlockingStub(channel);

        PAddItemRequest addItemRequest = PAddItemRequest.newBuilder()
                .setCustomerId(1)
                .addItems(PItem.newBuilder().setProductId(102).setQuantity(1).build())
                .build();
        Int32Value res = blockingStub.addItemToCart(addItemRequest);
        System.out.println("Add: " + res);

        PCart response = blockingStub.getCustomerCart(Int32Value.of(1));
        System.out.println(response);

        channel.shutdown();
    }
}
