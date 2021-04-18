package au.com.nab.icommerce.product.query.repository;

import au.com.nab.icommerce.product.query.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, Integer>, ProductRepositoryCustom {

}
