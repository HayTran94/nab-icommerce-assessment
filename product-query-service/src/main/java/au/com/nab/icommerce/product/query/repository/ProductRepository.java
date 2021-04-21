package au.com.nab.icommerce.product.query.repository;

import au.com.nab.icommerce.product.query.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Integer>, ProductRepositoryCustom {
    List<Product> findAllByIdIn(List<Integer> productIds);
}
