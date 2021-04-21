import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.cart.protobuf.PCartItem;
import au.com.nab.icommerce.cart.protobuf.PCartResponse;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Test {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8301)
                .usePlaintext()
                .build();

        CartServiceGrpc.CartServiceBlockingStub blockingStub = CartServiceGrpc.newBlockingStub(channel);

        PAddToCartRequest addItemRequest = PAddToCartRequest.newBuilder()
                .setCustomerId(1)
                .addItems(PCartItem.newBuilder().setProductId(102).setQuantity(1).build())
                .build();
        Int32Value res = blockingStub.addItemsToCart(addItemRequest);
        System.out.println("Add: " + res);

        PCartResponse response = blockingStub.getCustomerCart(Int32Value.of(1));
        System.out.println(response);

        channel.shutdown();
    }
}
