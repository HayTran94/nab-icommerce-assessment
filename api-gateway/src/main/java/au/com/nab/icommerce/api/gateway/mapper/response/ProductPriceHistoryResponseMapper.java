package au.com.nab.icommerce.api.gateway.mapper.response;

import au.com.nab.icommerce.api.gateway.dto.response.ProductPriceHistoryResponse;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductPriceHistoryResponseMapper extends ProtobufMapper<ProductPriceHistoryResponse, PProductPriceHistory> {
}