package au.com.nab.icommerce.product.query.service;

import au.com.nab.icommerce.product.protobuf.*;
import com.google.protobuf.Int32Value;

public interface ProductService {
    PProductResponse getProductById(Int32Value id);

    PProductMapResponse mGetProductsByIds(PProductIdsRequest pProductIdsRequest);

    PProductsResponse getProductsByCriteria(PProductCriteriaRequest criteria);
}
