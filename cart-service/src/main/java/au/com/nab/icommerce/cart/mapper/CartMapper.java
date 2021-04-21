package au.com.nab.icommerce.cart.mapper;

import au.com.nab.icommerce.cart.domain.Cart;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProtobufMapperConfig.class, uses = ItemMapper.class)
public interface CartMapper extends ProtobufMapper<Cart, PCart> {

    @Override
    @Mapping(source = "itemsList", target = "items")
    Cart toDomain(PCart protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    PCart toProtobuf(Cart domain);

}
