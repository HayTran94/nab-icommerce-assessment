package au.com.nab.icommerce.customer.service.mapper;

import au.com.nab.icommerce.customer.service.entity.Customer;
import au.com.nab.icommerce.customer.service.protobuf.PCustomer;

public class CustomerMapper {

    public static Customer toEntity(PCustomer pCustomer) {
        Customer entity = new Customer();
        entity.setId(pCustomer.getId());
        entity.setName(pCustomer.getName());
        entity.setEmail(pCustomer.getEmail());
        entity.setProvider(pCustomer.getProvider());
        entity.setProviderId(pCustomer.getProviderId());
        entity.setPhotoUrl(pCustomer.getPhotoUrl());
        entity.setToken(pCustomer.getToken());

        return entity;
    }

    public static PCustomer toProtobuf(Customer customer) {
        PCustomer.Builder protobuf = PCustomer.newBuilder();
        protobuf.setId(customer.getId());
        protobuf.setName(customer.getName());
        protobuf.setEmail(customer.getEmail());
        protobuf.setProvider(customer.getProvider());
        protobuf.setProviderId(customer.getProviderId());
        protobuf.setPhotoUrl(customer.getPhotoUrl());
        protobuf.setToken(customer.getToken());

        return protobuf.build();
    }
}
