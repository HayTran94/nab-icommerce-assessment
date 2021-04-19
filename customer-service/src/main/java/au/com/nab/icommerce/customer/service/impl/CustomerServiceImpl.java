package au.com.nab.icommerce.customer.service.impl;

import au.com.nab.icommerce.common.constant.ErrorCode;
import au.com.nab.icommerce.customer.domain.Customer;
import au.com.nab.icommerce.customer.mapper.CustomerMapper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
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

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Int32Value createCustomer(PCustomer pCustomer) {
        int res = ErrorCode.FAILED;
        try {
            Customer customer = customerMapper.toDomain(pCustomer);
            customer = customerRepository.save(customer);
            if (customer.getId() > 0) {
                res = customer.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public PCustomer getCustomerById(Int32Value id) {
        PCustomer pCustomer = null;
        try {
            Optional<Customer> customer = customerRepository.findById(id.getValue());
            if (customer.isPresent()) {
                pCustomer = customerMapper.toProtobuf(customer.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pCustomer;
    }
}
