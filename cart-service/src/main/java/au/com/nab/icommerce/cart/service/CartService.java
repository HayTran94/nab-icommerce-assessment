package au.com.nab.icommerce.cart.service;

import au.com.nab.icommerce.cart.protobuf.PAddItemRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import com.google.protobuf.Int32Value;

public interface CartService {
    Int32Value addItemToCart(PAddItemRequest addItemRequest);

    PCart getCartByCustomerId(Int32Value customerId);
}
