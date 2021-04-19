package au.com.nab.icommerce.product.auditing.repository;

import au.com.nab.icommerce.product.auditing.domain.ProductPriceHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductPriceHistoryRepository extends CrudRepository<ProductPriceHistory, String> {

    List<ProductPriceHistory> findAllByProductId(Integer productId);

}
