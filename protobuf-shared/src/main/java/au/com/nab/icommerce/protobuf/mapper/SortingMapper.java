package au.com.nab.icommerce.protobuf.mapper;

import au.com.nab.icommerce.common.protobuf.PSorting;
import au.com.nab.icommerce.protobuf.domain.Sorting;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = DirectionMapper.class)
public interface SortingMapper extends ProtobufMapper<Sorting, PSorting> {
}
