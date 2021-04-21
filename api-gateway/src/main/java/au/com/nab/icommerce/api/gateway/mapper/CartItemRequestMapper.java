package au.com.nab.icommerce.api.gateway.mapper;

import au.com.nab.icommerce.api.gateway.dto.CartItemRequest;
import au.com.nab.icommerce.cart.protobuf.PCartItem;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface CartItemRequestMapper extends ProtobufMapper<CartItemRequest, PCartItem> {
    CartItemRequestMapper INSTANCE = Mappers.getMapper(CartItemRequestMapper.class);
}
