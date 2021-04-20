package au.com.nab.icommerce.product.auditing.mapper;

import au.com.nab.icommerce.product.auditing.domain.ProductPriceHistory;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductPriceHistoryMapper extends ProtobufMapper<ProductPriceHistory, PProductPriceHistory> {
    ProductPriceHistoryMapper INSTANCE = Mappers.getMapper(ProductPriceHistoryMapper.class);
}
