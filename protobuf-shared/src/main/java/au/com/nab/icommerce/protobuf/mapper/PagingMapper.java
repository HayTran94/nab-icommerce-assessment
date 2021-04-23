package au.com.nab.icommerce.protobuf.mapper;

import au.com.nab.icommerce.common.protobuf.PPaging;
import au.com.nab.icommerce.protobuf.domain.Paging;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PagingMapper extends ProtobufMapper<Paging, PPaging> {
    PagingMapper INSTANCE = Mappers.getMapper(PagingMapper.class);
}