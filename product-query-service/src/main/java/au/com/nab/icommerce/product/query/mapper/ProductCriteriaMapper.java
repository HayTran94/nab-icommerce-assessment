package au.com.nab.icommerce.product.query.mapper;

import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;

public class ProductCriteriaMapper {

    public static ProductCriteria toDto(PProductCriteria protobuf) {
        ProductCriteria entity = new ProductCriteria();
        entity.setName(protobuf.getName());
        entity.setBrand(protobuf.getBrand());
        entity.setPriceFrom(protobuf.getPriceFrom());
        entity.setPriceTo(protobuf.getPriceTo());
        entity.setColor(protobuf.getColor());
        entity.setUnit(protobuf.getUnit());
        entity.setPageIndex(protobuf.getPageIndex());
        entity.setPageSize(protobuf.getPageSize());

        return entity;
    }
}
