package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServiceClient {

    @Autowired
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    public Integer addItemsToCart(PAddToCartRequest addToCartRequest) {
        Int32Value res = cartServiceBlockingStub.addItemsToCart(addToCartRequest);
        return res.getValue();
    }

    public PCart getCustomerCart(Integer customerId) {
        return cartServiceBlockingStub.getCustomerCart(Int32Value.of(customerId));
    }

    public Integer clearCustomerCart(Integer customerId) {
        Int32Value res = cartServiceBlockingStub.clearCustomerCart(Int32Value.of(customerId));
        return res.getValue();
    }
}
