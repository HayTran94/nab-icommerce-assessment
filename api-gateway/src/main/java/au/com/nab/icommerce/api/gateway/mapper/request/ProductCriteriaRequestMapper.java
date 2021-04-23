package au.com.nab.icommerce.api.gateway.mapper.request;

import au.com.nab.icommerce.api.gateway.dto.request.ProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductCriteriaRequestMapper extends ProtobufMapper<ProductCriteriaRequest, PProductCriteriaRequest> {
    ProductCriteriaRequestMapper INSTANCE = Mappers.getMapper(ProductCriteriaRequestMapper.class);
}
