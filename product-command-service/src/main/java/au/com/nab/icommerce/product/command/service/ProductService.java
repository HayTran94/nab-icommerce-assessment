package au.com.nab.icommerce.product.command.service;

import au.com.nab.icommerce.product.protobuf.PProduct;
import com.google.protobuf.Int32Value;

public interface ProductService {
    Int32Value createProduct(PProduct pProduct);

    Int32Value updateProduct(PProduct pProduct);
}
