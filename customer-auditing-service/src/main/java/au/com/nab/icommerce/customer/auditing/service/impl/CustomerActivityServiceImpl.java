package au.com.nab.icommerce.customer.auditing.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.customer.auditing.domain.CustomerActivity;
import au.com.nab.icommerce.customer.auditing.mapper.CustomerActivityMapper;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesRequest;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesResponse;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.customer.auditing.repository.CustomerActivityRepository;
import au.com.nab.icommerce.customer.auditing.service.CustomerActivityService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerActivityServiceImpl implements CustomerActivityService {

    @Autowired
    private CustomerActivityRepository customerActivityRepository;

    @Autowired
    private CustomerActivityMapper customerActivityMapper;

    @Override
    public Int32Value addCustomerActivity(PCustomerActivity pCustomerActivity) {
        int res = ErrorCode.FAILED;
        try {
            CustomerActivity customerActivity = customerActivityMapper.toDomain(pCustomerActivity);
            customerActivityRepository.save(customerActivity);
            res = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public Int32Value addCustomerActivities(PCustomerActivitiesRequest pCustomerActivityListRequest) {
        int res = ErrorCode.FAILED;
        try {
            List<PCustomerActivity> pCustomerActivities = pCustomerActivityListRequest.getCustomerActivitiesList();
            List<CustomerActivity> customerActivities = customerActivityMapper.toDomainList(pCustomerActivities);
            customerActivityRepository.saveAll(customerActivities);
            res = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public PCustomerActivitiesResponse getCustomerActivitiesByCustomerId(Int32Value customerId) {
        List<PCustomerActivity> pCustomerActivities = Collections.emptyList();
        try {
            List<CustomerActivity> customerActivities = customerActivityRepository.findAllByCustomerId(customerId.getValue());
            pCustomerActivities = customerActivityMapper.toProtobufList(customerActivities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PCustomerActivitiesResponse.newBuilder().addAllData(pCustomerActivities).build();
    }

}
