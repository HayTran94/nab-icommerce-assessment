package au.com.nab.icommerce.api.gateway.service;

import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.ProductCriteriaRequest;
import au.com.nab.icommerce.api.gateway.dto.ProductRequest;

public interface ProductService {

    ApiMessage getProductById(Integer productId);

    ApiMessage getProductsByCriteria(ProductCriteriaRequest productCriteriaRequest);

    ApiMessage createProduct(ProductRequest productRequest);

    ApiMessage updateProduct(ProductRequest productRequest);
}
