package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.OrderItem;
import au.com.nab.icommerce.order.protobuf.POrderItem;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = ProtobufMapperConfig.class)
public interface OrderItemMapper extends ProtobufMapper<OrderItem, POrderItem> {
}
