package au.com.nab.icommerce.product.query.service;

import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;

public interface ProductService {
    PProduct getProductById(Int32Value id);

    PProductsResponse getProductsByCriteria(PProductCriteria criteria);
}
