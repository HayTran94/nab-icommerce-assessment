package au.com.nab.icommerce.api.gateway.mapper;

import au.com.nab.icommerce.api.gateway.dto.AddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class, uses = CartItemRequestMapper.class)
public interface AddToCartRequestMapper extends ProtobufMapper<AddToCartRequest, PAddToCartRequest> {
    AddToCartRequestMapper INSTANCE = Mappers.getMapper(AddToCartRequestMapper.class);

    @Override
    @Mapping(target = "items", expression = "java(CartItemRequestMapper.INSTANCE.toDomainList(protobuf.getItemsList()))")
    AddToCartRequest toDomain(PAddToCartRequest protobuf);

    @Override
    @Mapping(target = "itemsList", expression = "java(CartItemRequestMapper.INSTANCE.toProtobufList(domain.getItems()))")
    PAddToCartRequest toProtobuf(AddToCartRequest domain);

}
