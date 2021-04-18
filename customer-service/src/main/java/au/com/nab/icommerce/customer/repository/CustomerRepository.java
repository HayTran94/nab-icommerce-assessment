package au.com.nab.icommerce.customer.repository;

import au.com.nab.icommerce.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
