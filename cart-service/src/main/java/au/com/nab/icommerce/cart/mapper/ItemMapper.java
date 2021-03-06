package au.com.nab.icommerce.cart.mapper;

import au.com.nab.icommerce.cart.domain.CartItem;
import au.com.nab.icommerce.cart.protobuf.PCartItem;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ItemMapper extends ProtobufMapper<CartItem, PCartItem> {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);
}
