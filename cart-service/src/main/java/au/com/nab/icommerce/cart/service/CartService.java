package au.com.nab.icommerce.cart.service;

import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PCartResponse;
import com.google.protobuf.Int32Value;

public interface CartService {
    Int32Value addItemsToCart(PAddToCartRequest addToCartRequest);

    PCartResponse getCustomerCart(Int32Value customerId);

    Int32Value clearCustomerCart(Int32Value customerId);
}
