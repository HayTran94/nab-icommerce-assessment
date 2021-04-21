package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProtobufMapperConfig.class, uses = {OrderItemMapper.class, OrderStatusMapper.class})
public interface OrderMapper extends ProtobufMapper<Order, POrder> {

    @Override
    @Mapping(source = "itemsList", target = "items")
    Order toDomain(POrder protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    POrder toProtobuf(Order domain);
}
