package au.com.nab.icommerce.cart.repository;

import au.com.nab.icommerce.cart.domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, String> {

}
