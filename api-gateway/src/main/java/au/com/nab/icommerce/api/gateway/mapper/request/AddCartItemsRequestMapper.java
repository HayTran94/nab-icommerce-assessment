package au.com.nab.icommerce.api.gateway.mapper.request;

import au.com.nab.icommerce.api.gateway.dto.request.AddCartItemsRequest;
import au.com.nab.icommerce.cart.protobuf.PAddCartItemsRequest;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface AddCartItemsRequestMapper extends ProtobufMapper<AddCartItemsRequest, PAddCartItemsRequest> {

    AddCartItemsRequestMapper INSTANCE = Mappers.getMapper(AddCartItemsRequestMapper.class);

    @Override
    @Mapping(source = "itemsList", target = "items")
    AddCartItemsRequest toDomain(PAddCartItemsRequest protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    PAddCartItemsRequest toProtobuf(AddCartItemsRequest domain);

}
