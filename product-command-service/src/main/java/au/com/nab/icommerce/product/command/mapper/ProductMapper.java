package au.com.nab.icommerce.product.command.mapper;

import au.com.nab.icommerce.product.command.domain.Product;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = ProtobufMapperConfig.class)
public interface ProductMapper extends ProtobufMapper<Product, PProduct> {
}
