package au.com.nab.icommerce.cart.service.impl;

import au.com.nab.icommerce.cart.cache.CacheKeyManager;
import au.com.nab.icommerce.cart.domain.Cart;
import au.com.nab.icommerce.cart.domain.CartItem;
import au.com.nab.icommerce.cart.mapper.CartMapper;
import au.com.nab.icommerce.cart.mapper.ItemMapper;
import au.com.nab.icommerce.cart.protobuf.*;
import au.com.nab.icommerce.cart.repository.CartRepository;
import au.com.nab.icommerce.cart.service.CartService;
import au.com.nab.icommerce.common.error.ErrorCode;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    private final CartMapper cartMapper = CartMapper.INSTANCE;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @Override
    public Int32Value addItemsToCart(PAddToCartRequest addToCartRequest) {
        int response = ErrorCode.FAILED;
        try {
            int customerId = addToCartRequest.getCustomerId();
            List<PCartItem> pItems = addToCartRequest.getItemsList();
            List<CartItem> newCartItems = itemMapper.toDomainList(pItems);

            String cartCacheKey = CacheKeyManager.getCartCacheKey(customerId);
            Optional<Cart> cartOptional = cartRepository.findById(cartCacheKey);
            Cart cart = cartOptional.orElseGet(() -> {
                Cart init = new Cart();
                init.setId(cartCacheKey);
                init.setCustomerId(customerId);
                return init;
            });

            List<CartItem> currentCartItems = cart.getItems() != null ? cart.getItems() : new ArrayList<>();
            Map<Integer, CartItem> curItemMap = currentCartItems.stream()
                    .collect(Collectors.toMap(CartItem::getProductId,
                            Function.identity(),
                            (v1, v2) -> v1,
                            LinkedHashMap::new));

            for (CartItem newCartItem : newCartItems) {
                // Set added time for new item
                if (newCartItem.getDateTime() == 0) {
                    newCartItem.setDateTime(System.currentTimeMillis());
                }

                // Add new item into current items
                CartItem curCartItem = curItemMap.get(newCartItem.getProductId());
                if (curCartItem != null) {
                    curCartItem.setQuantity(newCartItem.getQuantity());
                } else {
                    curItemMap.put(newCartItem.getProductId(), newCartItem);
                    currentCartItems.add(newCartItem);
                }
            }

            cartRepository.save(cart);
            response = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int32Value.of(response);
    }

    @Override
    public PCartResponse getCustomerCart(Int32Value customerId) {
        PCartResponse.Builder response = PCartResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            String cartCacheKey = CacheKeyManager.getCartCacheKey(customerId.getValue());
            Optional<Cart> cartOptional = cartRepository.findById(cartCacheKey);
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                PCart pCart = cartMapper.toProtobuf(cart);
                return response.setCode(ErrorCode.SUCCESS).setData(pCart).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

    @Override
    public Int32Value clearCustomerCart(Int32Value customerId) {
        int response = ErrorCode.FAILED;
        try {
            String cartCacheKey = CacheKeyManager.getCartCacheKey(customerId.getValue());
            cartRepository.deleteById(cartCacheKey);
            response = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }
}
