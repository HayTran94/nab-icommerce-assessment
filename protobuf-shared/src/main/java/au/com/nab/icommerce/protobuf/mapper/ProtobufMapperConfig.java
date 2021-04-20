package au.com.nab.icommerce.protobuf.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

@MapperConfig(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {
                SortingMapper.class, DirectionMapper.class, PagingMapper.class
        }
)
public class ProtobufMapperConfig {
}
