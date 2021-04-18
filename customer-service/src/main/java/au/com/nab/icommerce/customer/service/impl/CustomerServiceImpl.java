package au.com.nab.icommerce.customer.service.impl;

import au.com.nab.icommerce.customer.constant.AppConstant;
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

    @Override
    public Int32Value createCustomer(PCustomer pCustomer) {
        int res = AppConstant.FAILED;
        try {
            Customer customer = CustomerMapper.toDomain(pCustomer);
            customer = customerRepository.save(customer);
            if (customer.getId() > 0) {
                res = AppConstant.SUCCESS;
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
                pCustomer = CustomerMapper.toProtobuf(customer.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pCustomer;
    }
}
