package au.com.nab.icommerce.customer.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.customer.domain.Customer;
import au.com.nab.icommerce.customer.mapper.CustomerMapper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.protobuf.PCustomerResponse;
import au.com.nab.icommerce.customer.protobuf.PSocialInfoRequest;
import au.com.nab.icommerce.customer.repository.CustomerRepository;
import au.com.nab.icommerce.customer.service.CustomerService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Override
    public Int32Value createOrUpdateCustomer(PCustomer pCustomer) {
        int response = ErrorCode.FAILED;
        try {
            Customer customer = customerMapper.toDomain(pCustomer);
            customer = customerRepository.save(customer);
            if (customer.getId() > 0) {
                response = customer.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }

    @Override
    public PCustomerResponse getCustomerById(Int32Value id) {
        PCustomerResponse.Builder response = PCustomerResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            Optional<Customer> customerOptional = customerRepository.findById(id.getValue());
            if (customerOptional.isPresent()) {
                PCustomer pCustomer = customerMapper.toProtobuf(customerOptional.get());
                return response.setCode(ErrorCode.SUCCESS).setData(pCustomer).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

    @Override
    public PCustomerResponse getCustomerBySocialInfo(PSocialInfoRequest pSocialInfoRequest) {
        PCustomerResponse.Builder response = PCustomerResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            String provider = pSocialInfoRequest.getProvider();
            String providerId = pSocialInfoRequest.getProviderId();
            Customer customer = customerRepository.findByProviderAndProviderId(provider, providerId);
            if (customer != null) {
                PCustomer pCustomer = customerMapper.toProtobuf(customer);
                return response.setCode(ErrorCode.SUCCESS).setData(pCustomer).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }
}
