package au.com.nab.icommerce.api.gateway.mapper.request;

import au.com.nab.icommerce.api.gateway.dto.request.RemoveCartItemsRequest;
import au.com.nab.icommerce.cart.protobuf.PRemoveCartItemsRequest;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface RemoveCartItemsRequestMapper extends ProtobufMapper<RemoveCartItemsRequest, PRemoveCartItemsRequest> {
    RemoveCartItemsRequestMapper INSTANCE = Mappers.getMapper(RemoveCartItemsRequestMapper.class);

    @Override
    @Mapping(source = "productIdsList", target = "productIds")
    RemoveCartItemsRequest toDomain(PRemoveCartItemsRequest protobuf);

    @Override
    @Mapping(source = "productIds", target = "productIdsList")
    PRemoveCartItemsRequest toProtobuf(RemoveCartItemsRequest domain);

}
