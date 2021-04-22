package au.com.nab.icommerce.api.gateway.mapper.response;

import au.com.nab.icommerce.api.gateway.dto.response.CustomerActivityResponse;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapper;
import au.com.nab.icommerce.protobuf.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = ProtobufMapperConfig.class)
public interface CustomerActivityResponseMapper extends ProtobufMapper<CustomerActivityResponse, PCustomerActivity> {
}