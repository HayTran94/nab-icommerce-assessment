package au.com.nab.icommerce.protobuf.mapper;

import au.com.nab.icommerce.common.protobuf.PDirection;
import au.com.nab.icommerce.protobuf.domain.Direction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DirectionMapper extends ProtobufMapper<Direction, PDirection> {

    DirectionMapper INSTANCE = Mappers.getMapper(DirectionMapper.class);

    @Override
    @InheritInverseConfiguration
    Direction toDomain(PDirection protobuf);

    @ValueMapping(source = MappingConstants.NULL, target = "UNRECOGNIZED")
    @ValueMapping(source = "ASC", target = "ASC")
    @ValueMapping(source = "DESC", target = "DESC")
    PDirection toProtobuf(Direction domain);

}