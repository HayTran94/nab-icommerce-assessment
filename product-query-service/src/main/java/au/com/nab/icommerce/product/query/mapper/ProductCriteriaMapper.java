package au.com.nab.icommerce.product.query.mapper;

import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductCriteriaMapper extends ProtobufMapper<ProductCriteria, PProductCriteria> {
    ProductCriteriaMapper INSTANCE = Mappers.getMapper(ProductCriteriaMapper.class);
}
