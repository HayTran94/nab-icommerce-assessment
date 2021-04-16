package au.com.nab.icommerce.product.repository;

import au.com.nab.icommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
