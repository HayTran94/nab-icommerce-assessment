package au.com.nab.icommerce.customer.service.mapper;

import au.com.nab.icommerce.customer.service.entity.Customer;
import au.com.nab.icommerce.customer.service.protobuf.PCustomer;

//@Mapper(componentModel = "spring")
public interface CustomerMapper {

//    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    //    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "provider", target = "provider")
//    @Mapping(source = "providerId", target = "providerId")
//    @Mapping(source = "photoUrl", target = "photoUrl")
//    @Mapping(source = "token", target = "token")
    static Customer toEntity(PCustomer pCustomer) {
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

    //    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "provider", target = "provider")
//    @Mapping(source = "providerId", target = "providerId")
//    @Mapping(source = "photoUrl", target = "photoUrl")
//    @Mapping(source = "token", target = "token")
    static PCustomer toProtobuf(Customer customer) {
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
