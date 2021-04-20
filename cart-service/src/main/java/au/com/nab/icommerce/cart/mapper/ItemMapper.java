package au.com.nab.icommerce.cart.mapper;

import au.com.nab.icommerce.cart.domain.Item;
import au.com.nab.icommerce.cart.protobuf.PItem;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ItemMapper extends ProtobufMapper<Item, PItem> {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);
}
