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
    public PProduct toProtobuf(Product domain) {
        PProduct.Builder protobuf = PProduct.newBuilder();
        protobuf.setId(domain.getId());
        protobuf.setName(domain.getName());
        protobuf.setBrand(domain.getBrand());
        protobuf.setPrice(domain.getPrice());
        protobuf.setColor(domain.getColor());
        protobuf.setImage(domain.getImage());
        protobuf.setUnit(domain.getUnit());

        return protobuf.build();
    }

}
