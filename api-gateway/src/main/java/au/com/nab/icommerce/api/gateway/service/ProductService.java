package au.com.nab.icommerce.api.gateway.service;

import au.com.nab.icommerce.api.gateway.dto.request.ProductCriteria;
import au.com.nab.icommerce.api.gateway.dto.response.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    List<Product> getProductsByCriteria(ProductCriteria productCriteria);

    Integer createProduct(Product product);

    Integer updateProduct(Product product);
}
