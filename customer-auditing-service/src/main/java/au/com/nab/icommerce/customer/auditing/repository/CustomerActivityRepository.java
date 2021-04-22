package au.com.nab.icommerce.customer.auditing.repository;


import au.com.nab.icommerce.customer.auditing.domain.CustomerActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerActivityRepository extends CrudRepository<CustomerActivity, String> {
    List<CustomerActivity> findAllByCustomerIdOrderByDateTimeDesc(Integer customerId);
}
