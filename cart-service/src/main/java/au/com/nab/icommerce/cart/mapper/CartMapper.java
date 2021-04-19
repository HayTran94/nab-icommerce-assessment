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
        entity.setCustomerName(protobuf.getCustomerName());
        entity.setItems(itemMapper.toDomain(protobuf.getItemsList()));

        return entity;
    }

    @Override
    public PCart toProtobuf(Cart entity) {
        PCart.Builder protobuf = PCart.newBuilder();
        protobuf.setId(entity.getId());
        protobuf.setCustomerId(entity.getCustomerId());
        protobuf.setCustomerName(entity.getCustomerName());
        protobuf.addAllItems(itemMapper.toProtobuf(entity.getItems()));

        return protobuf.build();
    }

}
