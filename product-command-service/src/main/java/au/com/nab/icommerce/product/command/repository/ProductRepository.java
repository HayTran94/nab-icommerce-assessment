package au.com.nab.icommerce.product.command.repository;

import au.com.nab.icommerce.product.command.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
