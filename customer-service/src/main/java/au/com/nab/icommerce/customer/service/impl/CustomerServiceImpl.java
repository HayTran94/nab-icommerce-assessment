package au.com.nab.icommerce.customer.service.impl;

import au.com.nab.icommerce.customer.constant.AppConstant;
import au.com.nab.icommerce.customer.entity.Customer;
import au.com.nab.icommerce.customer.mapper.CustomerMapper;
import au.com.nab.icommerce.customer.protobuf.CustomerResponse;
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
        int res = AppConstant.FAILED;
        try {
            Customer customer = CustomerMapper.toEntity(request);
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
    public CustomerResponse getCustomerById(Int32Value request) {
        PCustomer pCustomer = null;
        try {
            Customer customer = customerRepository.findCustomerById(request.getValue());
            pCustomer = CustomerMapper.toProtobuf(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomerResponse.newBuilder().setData(pCustomer).build();
    }
}
