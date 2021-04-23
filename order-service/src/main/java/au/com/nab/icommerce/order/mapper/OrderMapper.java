package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.domain.OrderItem;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(config = ProtobufMapperConfig.class, uses = {OrderItemMapper.class, OrderStatusMapper.class})
public interface OrderMapper extends ProtobufMapper<Order, POrder> {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Override
    @Mapping(source = "itemsList", target = "items")
    Order toDomain(POrder protobuf);

    @Override
    @Mapping(source = "items", target = "itemsList")
    POrder toProtobuf(Order domain);

    @AfterMapping
    default void afterMappingToDomain(@MappingTarget Order order) {
        List<OrderItem> orderItems = order.getItems();
        if (CollectionUtils.isEmpty(orderItems)) {
            return;
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
    }
}
