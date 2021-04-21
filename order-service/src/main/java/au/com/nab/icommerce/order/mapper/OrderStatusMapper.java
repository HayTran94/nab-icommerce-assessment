package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.OrderStatus;
import au.com.nab.icommerce.order.protobuf.POrderStatus;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface OrderStatusMapper extends ProtobufMapper<OrderStatus, POrderStatus> {
    OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);

    @Override
    @InheritInverseConfiguration
    OrderStatus toDomain(POrderStatus protobuf);

    @ValueMapping(source = MappingConstants.NULL, target = "UNRECOGNIZED")
    @ValueMapping(source = "UNKNOWN", target = "UNKNOWN")
    @ValueMapping(source = "INIT", target = "INIT")
    @ValueMapping(source = "PROCESSING", target = "PROCESSING")
    @ValueMapping(source = "DELIVERING", target = "DELIVERING")
    @ValueMapping(source = "COMPLETED", target = "COMPLETED")
    @ValueMapping(source = "DROP_OFF_FAIL", target = "DROP_OFF_FAIL")
    POrderStatus toProtobuf(OrderStatus domain);

}
