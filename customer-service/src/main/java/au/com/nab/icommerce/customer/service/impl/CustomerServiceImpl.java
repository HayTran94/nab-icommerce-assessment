package au.com.nab.icommerce.customer.service.impl;

import au.com.nab.icommerce.customer.constant.AppConstant;
import au.com.nab.icommerce.customer.entity.Customer;
import au.com.nab.icommerce.customer.mapper.CustomerMapper;
import au.com.nab.icommerce.customer.protobuf.GetCustomerResponse;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.repository.CustomerRepository;
import au.com.nab.icommerce.customer.service.CustomerService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Int32Value createCustomer(PCustomer request) {
        Customer customer = CustomerMapper.toEntity(request);
        customer = customerRepository.save(customer);

        int res = AppConstant.FAILED;
        if (customer.getId() > 0) {
            res = AppConstant.SUCCESS;
        }

        return Int32Value.of(res);
    }

    @Override
    public GetCustomerResponse getCustomerById(Int32Value request) {
        Customer customer = customerRepository.findCustomerById(request.getValue());
        PCustomer pCustomer = CustomerMapper.toProtobuf(customer);
        return GetCustomerResponse.newBuilder().setData(pCustomer).build();
    }
}
