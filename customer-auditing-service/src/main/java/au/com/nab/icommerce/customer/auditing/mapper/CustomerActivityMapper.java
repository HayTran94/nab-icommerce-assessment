package au.com.nab.icommerce.customer.auditing.mapper;

import au.com.nab.icommerce.customer.auditing.domain.CustomerActivity;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface CustomerActivityMapper extends ProtobufMapper<CustomerActivity, PCustomerActivity> {
}
