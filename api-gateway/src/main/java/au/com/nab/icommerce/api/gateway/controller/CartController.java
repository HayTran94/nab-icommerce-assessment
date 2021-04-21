package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.CartServiceClient;
import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.AddToCartRequest;
import au.com.nab.icommerce.api.gateway.dto.CartItemRequest;
import au.com.nab.icommerce.api.gateway.mapper.AddToCartRequestMapper;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.product.protobuf.PProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private AddToCartRequestMapper addToCartRequestMapper;

    @PostMapping
    public ApiMessage addItemsToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        try {
            List<CartItemRequest> cartItemRequests = addToCartRequest.getItems();
            if (CollectionUtils.isEmpty(cartItemRequests)) {
                return ApiMessage.CART_ITEMS_EMPTY;
            }

            // Check customer is existed
            PCustomer customer = customerServiceClient.getCustomerById(addToCartRequest.getCustomerId());
            if (customer == null) {
                return ApiMessage.CUSTOMER_NOT_FOUND;
            }

            // Check product is existed
            for (CartItemRequest cartItemRequest : cartItemRequests) {
                PProduct product = productServiceClient.getProductsById(cartItemRequest.getProductId());
                if (product == null) {
                    return ApiMessage.CART_ITEMS_INVALID;
                }
            }

            // Call cart service
            PAddToCartRequest pAddToCartRequest = addToCartRequestMapper.toProtobuf(addToCartRequest);
            int response = cartServiceClient.addItemsToCart(pAddToCartRequest);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.ADD_TO_CART_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/customer/{customerId}")
    public ApiMessage getCustomerCart(@PathVariable Integer customerId) {
        try {
            PCustomer customer = customerServiceClient.getCustomerById(customerId);
            if (customer == null) {
                return ApiMessage.CUSTOMER_NOT_FOUND;
            }

            PCart cart = cartServiceClient.getCustomerCart(customerId);
            if (cart == null) {
                return ApiMessage.CART_EMPTY;
            }

            return ApiMessage.success(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/customer/{customerId}")
    public ApiMessage clearCustomerCart(@PathVariable Integer customerId) {
        try {
            PCustomer customer = customerServiceClient.getCustomerById(customerId);
            if (customer == null) {
                return ApiMessage.CUSTOMER_NOT_FOUND;
            }

            int response = cartServiceClient.clearCustomerCart(customerId);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.CLEAR_CART_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
