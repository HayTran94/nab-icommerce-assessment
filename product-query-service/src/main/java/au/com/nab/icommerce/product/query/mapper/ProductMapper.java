package au.com.nab.icommerce.product.query.mapper;

import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.query.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toDomain(PProduct protobuf) {
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

    public static PProduct toProtobuf(Product entity) {
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

    public static List<PProduct> toProtobuf(List<Product> entities) {
        return entities.stream()
                .map(ProductMapper::toProtobuf)
                .collect(Collectors.toList());
    }
}
