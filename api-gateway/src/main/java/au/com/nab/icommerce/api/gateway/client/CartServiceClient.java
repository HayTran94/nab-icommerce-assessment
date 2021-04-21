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
    private CartServiceGrpc.CartServiceBlockingStub cartService;

    public Integer addItemsToCart(PAddToCartRequest addToCartRequest) {
        Int32Value res = cartService.addItemsToCart(addToCartRequest);
        return res.getValue();
    }

    public PCart getCustomerCart(Integer customerId) {
        return cartService.getCustomerCart(Int32Value.of(customerId));
    }

    public Integer clearCustomerCart(Integer customerId) {
        Int32Value res = cartService.clearCustomerCart(Int32Value.of(customerId));
        return res.getValue();
    }
}
