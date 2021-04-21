package au.com.nab.icommerce.api.gateway.service;


import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.AddToCartRequest;

public interface CartService {

    ApiMessage addItemsToCart(AddToCartRequest addToCartRequest);

    ApiMessage getCustomerCart(Integer customerId);

    ApiMessage clearCustomerCart(Integer customerId);

}
