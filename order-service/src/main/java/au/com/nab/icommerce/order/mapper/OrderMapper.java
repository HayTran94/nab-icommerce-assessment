package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class, uses = {OrderItemMapper.class, OrderStatusMapper.class})
public interface OrderMapper extends ProtobufMapper<Order, POrder> {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Override
    @Mapping(target = "items", expression = "java(OrderItemMapper.INSTANCE.toDomainList(protobuf.getItemsList()))")
    Order toDomain(POrder protobuf);

    @Override
    @Mapping(target = "itemsList", expression = "java(OrderItemMapper.INSTANCE.toProtobufList(domain.getItems()))")
    POrder toProtobuf(Order domain);
}
