package au.com.nab.icommerce.product.query.service;

import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductResponse;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;

public interface ProductService {
    PProductResponse getProductById(Int32Value id);

    PProductsResponse getProductsByCriteria(PProductCriteriaRequest criteria);
}
