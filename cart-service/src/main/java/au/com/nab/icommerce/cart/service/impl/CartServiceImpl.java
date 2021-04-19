package au.com.nab.icommerce.cart.service.impl;

import au.com.nab.icommerce.cart.cache.CacheKeyManager;
import au.com.nab.icommerce.cart.domain.Cart;
import au.com.nab.icommerce.cart.domain.Item;
import au.com.nab.icommerce.cart.mapper.CartMapper;
import au.com.nab.icommerce.cart.mapper.ItemMapper;
import au.com.nab.icommerce.cart.protobuf.PAddItemRequest;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.cart.protobuf.PItem;
import au.com.nab.icommerce.cart.repository.CartRepository;
import au.com.nab.icommerce.cart.service.CartService;
import au.com.nab.icommerce.common.constant.ErrorCode;
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

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Int32Value addItemToCart(PAddItemRequest addItemRequest) {
        int res = ErrorCode.FAILED;
        try {
            int customerId = addItemRequest.getCustomerId();
            List<PItem> pItems = addItemRequest.getItemsList();
            List<Item> newItems = itemMapper.toDomain(pItems);

            String cartCacheKey = CacheKeyManager.getCartCacheKey(customerId);
            Optional<Cart> cartOptional = cartRepository.findById(cartCacheKey);
            Cart cart = cartOptional.orElseGet(() -> {
                Cart init = new Cart();
                init.setId(cartCacheKey);
                init.setCustomerId(customerId);
                init.setCustomerName(addItemRequest.getCustomerName());
                return init;
            });

            List<Item> currentItems = cart.getItems() != null ? cart.getItems() : new ArrayList<>();
            Map<Integer, Item> curItemMap = currentItems.stream()
                    .collect(Collectors.toMap(Item::getProductId,
                            Function.identity(),
                            (v1, v2) -> v1,
                            LinkedHashMap::new));

            for (Item newItem : newItems) {
                // Set added time for new item
                if (newItem.getAddedTime() == 0) {
                    newItem.setAddedTime(System.currentTimeMillis());
                }

                // Add new item into current items
                Item curItem = curItemMap.get(newItem.getProductId());
                if (curItem != null) {
                    curItem.setQuantity(newItem.getQuantity());
                } else {
                    curItemMap.put(newItem.getProductId(), newItem);
                    currentItems.add(newItem);
                }
            }

            cartRepository.save(cart);
            res = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int32Value.of(res);
    }

    @Override
    public PCart getCartByCustomerId(Int32Value customerId) {
        PCart pCart = null;
        try {
            String cartCacheKey = CacheKeyManager.getCartCacheKey(customerId.getValue());
            Optional<Cart> cartOptional = cartRepository.findById(cartCacheKey);
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                pCart = cartMapper.toProtobuf(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pCart;
    }
}
