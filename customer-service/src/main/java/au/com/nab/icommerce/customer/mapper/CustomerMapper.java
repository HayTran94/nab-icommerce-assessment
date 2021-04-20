package au.com.nab.icommerce.customer.mapper;

import au.com.nab.icommerce.customer.domain.Customer;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface CustomerMapper extends ProtobufMapper<Customer, PCustomer> {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
}
