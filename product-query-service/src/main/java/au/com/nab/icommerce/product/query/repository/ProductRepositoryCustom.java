package au.com.nab.icommerce.product.query.repository;

import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findProductsByCriteria(ProductCriteria criteria);
}
