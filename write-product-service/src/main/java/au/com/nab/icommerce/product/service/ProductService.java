package au.com.nab.icommerce.product.service;

import au.com.nab.icommerce.product.protobuf.PProduct;
import com.google.protobuf.Int32Value;

public interface ProductService {
    Int32Value createProduct(PProduct request);

    Int32Value updateProduct(PProduct request);
}
