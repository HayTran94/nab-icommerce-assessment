package au.com.nab.icommerce.api.gateway.service.impl;

import au.com.nab.icommerce.api.gateway.client.CartServiceClient;
import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.AddToCartRequest;
import au.com.nab.icommerce.api.gateway.dto.CartItemRequest;
import au.com.nab.icommerce.api.gateway.mapper.AddToCartRequestMapper;
import au.com.nab.icommerce.api.gateway.service.CartService;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.protobuf.PProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private AddToCartRequestMapper addToCartRequestMapper;

    @Override
    public ApiMessage addItemsToCart(AddToCartRequest addToCartRequest) {
        try {
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
            Integer res = cartServiceClient.addItemsToCart(pAddToCartRequest);
            if (ErrorCodeHelper.isFail(res)) {
                return ApiMessage.ADD_TO_CART_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Override
    public ApiMessage getCustomerCart(Integer customerId) {
        try {
            PCart pCart = cartServiceClient.getCustomerCart(customerId);
            if (pCart == null) {
                return ApiMessage.NOT_FOUND;
            }

            return ApiMessage.success(pCart);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Override
    public ApiMessage clearCustomerCart(Integer customerId) {
        try {
            Integer res = cartServiceClient.clearCustomerCart(customerId);
            if (ErrorCodeHelper.isFail(res)) {
                return ApiMessage.CLEAR_CART_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
