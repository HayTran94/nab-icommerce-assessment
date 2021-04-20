package au.com.nab.icommerce.protobuf.mapper;

import au.com.nab.icommerce.common.protobuf.PDirection;
import au.com.nab.icommerce.protobuf.domain.Direction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DirectionMapper extends ProtobufMapper<Direction, PDirection> {

    DirectionMapper INSTANCE = Mappers.getMapper(DirectionMapper.class);

    @Override
    @ValueMapping(source = "ASC", target = "ASC")
    @ValueMapping(source = "DESC", target = "DESC")
    @ValueMapping(source = "UNRECOGNIZED", target = "ASC")
    @ValueMapping(source = MappingConstants.NULL, target = "ASC")
    Direction toDomain(PDirection protobuf);

    @ValueMapping(source = "ASC", target = "ASC")
    @ValueMapping(source = "DESC", target = "DESC")
    @ValueMapping(source = MappingConstants.NULL, target = "ASC")
    PDirection toProtobuf(Direction domain);

}