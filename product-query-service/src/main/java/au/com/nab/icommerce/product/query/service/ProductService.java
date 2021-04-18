package au.com.nab.icommerce.product.query.service;

import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductSearchCriteria;
import au.com.nab.icommerce.product.protobuf.ProductListResponse;
import com.google.protobuf.Int32Value;

public interface ProductService {
    PProduct getProductById(Int32Value id);

    ProductListResponse searchProductsByCriteria(PProductSearchCriteria criteria);
}
