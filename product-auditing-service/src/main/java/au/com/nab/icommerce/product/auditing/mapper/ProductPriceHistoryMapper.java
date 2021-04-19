package au.com.nab.icommerce.product.auditing.mapper;

import au.com.nab.icommerce.common.mapper.AbstractProtobufMapper;
import au.com.nab.icommerce.product.auditing.domain.ProductPriceHistory;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceHistoryMapper extends AbstractProtobufMapper<ProductPriceHistory, PProductPriceHistory> {

    @Override
    public ProductPriceHistory toDomain(PProductPriceHistory protobuf) {
        ProductPriceHistory entity = new ProductPriceHistory();
        entity.setId(protobuf.getId());
        entity.setProductId(protobuf.getProductId());
        entity.setOldPrice(protobuf.getOldPrice());
        entity.setNewPrice(protobuf.getNewPrice());
        entity.setDateTime(protobuf.getDateTime());
        return entity;
    }

    @Override
    public PProductPriceHistory toProtobuf(ProductPriceHistory entity) {
        PProductPriceHistory.Builder protobuf = PProductPriceHistory.newBuilder();
        protobuf.setId(entity.getId());
        protobuf.setProductId(entity.getProductId());
        protobuf.setOldPrice(entity.getOldPrice());
        protobuf.setNewPrice(entity.getNewPrice());
        protobuf.setDateTime(entity.getDateTime());
        return protobuf.build();
    }
}
