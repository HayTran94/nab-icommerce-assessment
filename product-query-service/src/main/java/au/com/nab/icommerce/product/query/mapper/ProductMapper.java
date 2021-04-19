package au.com.nab.icommerce.product.query.mapper;

import au.com.nab.icommerce.common.mapper.AbstractProtobufMapper;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.query.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractProtobufMapper<Product, PProduct> {

    @Override
    public Product toDomain(PProduct protobuf) {
        Product entity = new Product();
        entity.setId(protobuf.getId());
        entity.setName(protobuf.getName());
        entity.setBrand(protobuf.getBrand());
        entity.setPrice(protobuf.getPrice());
        entity.setColor(protobuf.getColor());
        entity.setImage(protobuf.getImage());
        entity.setUnit(protobuf.getUnit());

        return entity;
    }

    @Override
    public PProduct toProtobuf(Product entity) {
        PProduct.Builder protobuf = PProduct.newBuilder();
        protobuf.setId(entity.getId());
        protobuf.setName(entity.getName());
        protobuf.setBrand(entity.getBrand());
        protobuf.setPrice(entity.getPrice());
        protobuf.setColor(entity.getColor());
        protobuf.setImage(entity.getImage());
        protobuf.setUnit(entity.getUnit());

        return protobuf.build();
    }

}
