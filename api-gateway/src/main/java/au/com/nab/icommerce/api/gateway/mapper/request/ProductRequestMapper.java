package au.com.nab.icommerce.api.gateway.mapper.request;

import au.com.nab.icommerce.api.gateway.dto.request.ProductRequest;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductRequestMapper extends ProtobufMapper<ProductRequest, PProduct> {
    ProductRequestMapper INSTANCE = Mappers.getMapper(ProductRequestMapper.class);
}
