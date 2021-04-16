package au.com.nab.icommerce.customer.mapper;

import au.com.nab.icommerce.customer.entity.Customer;
import au.com.nab.icommerce.customer.protobuf.PCustomer;

public class CustomerMapper {

    public static Customer toEntity(PCustomer protobuf) {
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

    public static PCustomer toProtobuf(Customer entity) {
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
