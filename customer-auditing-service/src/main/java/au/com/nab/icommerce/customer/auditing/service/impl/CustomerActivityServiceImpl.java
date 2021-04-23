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

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerActivityServiceImpl implements CustomerActivityService {

    @Autowired
    private CustomerActivityRepository customerActivityRepository;

    private final CustomerActivityMapper customerActivityMapper = CustomerActivityMapper.INSTANCE;

    @Override
    public Int32Value addCustomerActivity(PCustomerActivity pCustomerActivity) {
        int response = ErrorCode.FAILED;
        try {
            CustomerActivity customerActivity = customerActivityMapper.toDomain(pCustomerActivity);
            customerActivityRepository.save(customerActivity);
            response = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }

    @Override
    public Int32Value addCustomerActivities(PCustomerActivitiesRequest pCustomerActivityListRequest) {
        int response = ErrorCode.FAILED;
        try {
            List<PCustomerActivity> pCustomerActivities = pCustomerActivityListRequest.getCustomerActivitiesList();
            List<CustomerActivity> customerActivities = customerActivityMapper.toDomainList(pCustomerActivities);
            customerActivityRepository.saveAll(customerActivities);
            response = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }

    @Override
    public PCustomerActivitiesResponse getCustomerActivitiesByCustomerId(Int32Value customerId) {
        PCustomerActivitiesResponse.Builder response = PCustomerActivitiesResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            List<CustomerActivity> customerActivities =
                    customerActivityRepository.findAllByCustomerIdOrderByDateTimeDesc(customerId.getValue());
            List<PCustomerActivity> pCustomerActivities = customerActivityMapper.toProtobufList(customerActivities);
            return response.setCode(ErrorCode.SUCCESS).addAllData(pCustomerActivities).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

}
