package au.com.nab.icommerce.customer.auditing.mapper;

import au.com.nab.icommerce.common.mapper.AbstractProtobufMapper;
import au.com.nab.icommerce.customer.auditing.domain.CustomerActivity;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import org.springframework.stereotype.Component;

@Component
public class CustomerActivityMapper extends AbstractProtobufMapper<CustomerActivity, PCustomerActivity> {

    @Override
    public CustomerActivity toDomain(PCustomerActivity protobuf) {
        CustomerActivity entity = new CustomerActivity();
        entity.setId(protobuf.getId());
        entity.setCustomerId(protobuf.getCustomerId());
        entity.setAction(protobuf.getAction());
        entity.setTime(protobuf.getTime());
        entity.setData(protobuf.getData());
        return entity;
    }

    @Override
    public PCustomerActivity toProtobuf(CustomerActivity domain) {
        PCustomerActivity.Builder protobuf = PCustomerActivity.newBuilder();
        protobuf.setId(domain.getId());
        protobuf.setCustomerId(domain.getCustomerId());
        protobuf.setAction(domain.getAction());
        protobuf.setTime(domain.getTime());
        protobuf.setData(domain.getData());
        return protobuf.build();
    }
}
