package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.cart.protobuf.PAddCartItemsRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.cart.protobuf.PCartResponse;
import au.com.nab.icommerce.cart.protobuf.PRemoveCartItemsRequest;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServiceClient {

    @Autowired
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    public int addCartItems(PAddCartItemsRequest addCartItemsRequest) {
        Int32Value response = cartServiceBlockingStub.addCartItems(addCartItemsRequest);
        return response.getValue();
    }

    public int removeCartItems(PRemoveCartItemsRequest removeCartItemsRequest) {
        Int32Value response = cartServiceBlockingStub.removeCartItems(removeCartItemsRequest);
        return response.getValue();
    }

    public PCart getCustomerCart(Integer customerId) {
        PCartResponse response = cartServiceBlockingStub.getCustomerCart(Int32Value.of(customerId));
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }

    public int clearCustomerCart(Integer customerId) {
        Int32Value response = cartServiceBlockingStub.clearCustomerCart(Int32Value.of(customerId));
        return response.getValue();
    }
}
