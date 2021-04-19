package au.com.nab.icommerce.customer.mapper;

import au.com.nab.icommerce.common.mapper.AbstractProtobufMapper;
import au.com.nab.icommerce.customer.domain.Customer;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends AbstractProtobufMapper<Customer, PCustomer> {

    @Override
    public Customer toDomain(PCustomer protobuf) {
        Customer entity = new Customer();
        entity.setId(protobuf.getId());
        entity.setName(protobuf.getName());
        entity.setEmail(protobuf.getEmail());
        entity.setProvider(protobuf.getProvider());
        entity.setProviderId(protobuf.getProviderId());
        entity.setPhotoUrl(protobuf.getPhotoUrl());
        entity.setToken(protobuf.getToken());
        return entity;
    }

    @Override
    public PCustomer toProtobuf(Customer entity) {
        PCustomer.Builder protobuf = PCustomer.newBuilder();
        protobuf.setId(entity.getId());
        protobuf.setName(entity.getName());
        protobuf.setEmail(entity.getEmail());
        protobuf.setProvider(entity.getProvider());
        protobuf.setProviderId(entity.getProviderId());
        protobuf.setPhotoUrl(entity.getPhotoUrl());
        protobuf.setToken(entity.getToken());
        return protobuf.build();
    }
}
