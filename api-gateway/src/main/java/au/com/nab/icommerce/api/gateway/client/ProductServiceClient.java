package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductResponse;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductServiceClient {

    @Autowired
    private ProductCommandServiceGrpc.ProductCommandServiceBlockingStub productCommandServiceBlockingStub;

    @Autowired
    private ProductQueryServiceGrpc.ProductQueryServiceBlockingStub productQueryServiceBlockingStub;

    public int createProduct(PProduct product) {
        Int32Value res = productCommandServiceBlockingStub.createProduct(product);
        return res.getValue();
    }

    public int updateProduct(PProduct product) {
        Int32Value res = productCommandServiceBlockingStub.updateProduct(product);
        return res.getValue();
    }

    public PProduct getProductsById(Integer productId) {
        PProductResponse response = productQueryServiceBlockingStub.getProductsById(Int32Value.of(productId));
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }

    public List<PProduct> getProductsByCriteria(PProductCriteriaRequest productCriteriaRequest) {
        PProductsResponse response = productQueryServiceBlockingStub.getProductsByCriteria(productCriteriaRequest);
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getDataList();
        }
        return Collections.emptyList();
    }
}
