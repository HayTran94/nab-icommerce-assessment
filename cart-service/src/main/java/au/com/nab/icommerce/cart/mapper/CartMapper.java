package au.com.nab.icommerce.cart.mapper;

import au.com.nab.icommerce.cart.domain.Cart;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.common.mapper.AbstractProtobufMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends AbstractProtobufMapper<Cart, PCart> {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Cart toDomain(PCart protobuf) {
        Cart entity = new Cart();
        entity.setId(protobuf.getId());
        entity.setCustomerId(protobuf.getCustomerId());
        entity.setItems(itemMapper.toDomain(protobuf.getItemsList()));

        return entity;
    }

    @Override
    public PCart toProtobuf(Cart domain) {
        PCart.Builder protobuf = PCart.newBuilder();
        protobuf.setId(domain.getId());
        protobuf.setCustomerId(domain.getCustomerId());
        protobuf.addAllItems(itemMapper.toProtobuf(domain.getItems()));

        return protobuf.build();
    }

}
