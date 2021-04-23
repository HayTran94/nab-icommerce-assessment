package au.com.nab.icommerce.api.gateway.mapper.response;

import au.com.nab.icommerce.api.gateway.dto.response.OrderResponse;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface OrderResponseMapper extends ProtobufMapper<OrderResponse, POrder> {

    OrderResponseMapper INSTANCE = Mappers.getMapper(OrderResponseMapper.class);

    @Override
    @Mapping(source = "itemsList", target = "items")
    OrderResponse toDomain(POrder protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    POrder toProtobuf(OrderResponse domain);

}