package au.com.nab.icommerce.cart.service;

import au.com.nab.icommerce.cart.protobuf.PAddItemRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import com.google.protobuf.Int32Value;

public interface CartService {
    Int32Value addItemToCart(PAddItemRequest addItemRequest);

    PCart getCustomerCart(Int32Value customerId);

    Int32Value clearCustomerCart(Int32Value customerId);
}
