package au.com.nab.icommerce.api.gateway.mapper.response;

import au.com.nab.icommerce.api.gateway.dto.response.CartResponse;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProtobufMapperConfig.class)
public interface CartResponseMapper extends ProtobufMapper<CartResponse, PCart> {

    @Override
    @Mapping(source = "itemsList", target = "items")
    CartResponse toDomain(PCart protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    PCart toProtobuf(CartResponse domain);

}