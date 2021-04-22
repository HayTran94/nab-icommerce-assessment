package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.aspect.CustomerActivity;
import au.com.nab.icommerce.api.gateway.client.CartServiceClient;
import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.AddToCartRequest;
import au.com.nab.icommerce.api.gateway.dto.request.CartItemRequest;
import au.com.nab.icommerce.api.gateway.mapper.request.AddToCartRequestMapper;
import au.com.nab.icommerce.api.gateway.mapper.response.CartResponseMapper;
import au.com.nab.icommerce.api.gateway.security.SecurityHelper;
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
@RequestMapping("/api/carts/customer")
public class CartController {

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private AddToCartRequestMapper addToCartRequestMapper;

    @Autowired
    private CartResponseMapper cartResponseMapper;

    @PostMapping
    @CustomerActivity("ADD_ITEMS_TO_CART")
    public ApiMessage addItemsToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != addToCartRequest.getCustomerId()) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            List<CartItemRequest> cartItemRequests = addToCartRequest.getItems();
            if (CollectionUtils.isEmpty(cartItemRequests)) {
                return ApiMessage.CART_ITEMS_EMPTY;
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
                return ApiMessage.CART_ADD_ITEMS_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{customerId}")
    @CustomerActivity("GET_CUSTOMER_CART")
    public ApiMessage getCustomerCart(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            PCart cart = cartServiceClient.getCustomerCart(customerId);
            if (cart == null) {
                return ApiMessage.CART_EMPTY;
            }

            return ApiMessage.success(cartResponseMapper.toDomain(cart));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{customerId}")
    @CustomerActivity("CLEAR_CUSTOMER_CART")
    public ApiMessage clearCustomerCart(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            int response = cartServiceClient.clearCustomerCart(customerId);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.CART_CLEAR_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
