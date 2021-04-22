package au.com.nab.icommerce.api.gateway.mapper.request;

import au.com.nab.icommerce.api.gateway.dto.request.AddToCartRequest;
import au.com.nab.icommerce.cart.protobuf.PAddToCartRequest;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProtobufMapperConfig.class)
public interface AddToCartRequestMapper extends ProtobufMapper<AddToCartRequest, PAddToCartRequest> {

    @Override
    @Mapping(source = "itemsList", target = "items")
    AddToCartRequest toDomain(PAddToCartRequest protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    PAddToCartRequest toProtobuf(AddToCartRequest domain);

}