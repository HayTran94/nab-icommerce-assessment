package au.com.nab.icommerce.cart.service;

import au.com.nab.icommerce.cart.protobuf.PAddCartItemsRequest;
import au.com.nab.icommerce.cart.protobuf.PCartResponse;
import au.com.nab.icommerce.cart.protobuf.PRemoveCartItemsRequest;
import com.google.protobuf.Int32Value;

public interface CartService {
    Int32Value addCartItems(PAddCartItemsRequest addToCartRequest);

    Int32Value removeCartItems(PRemoveCartItemsRequest removeItemsRequest);

    PCartResponse getCustomerCart(Int32Value customerId);

    Int32Value clearCustomerCart(Int32Value customerId);
}
