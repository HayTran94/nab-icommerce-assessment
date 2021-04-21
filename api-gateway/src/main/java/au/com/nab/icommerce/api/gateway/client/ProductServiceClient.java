package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceClient {

    @Autowired
    private ProductCommandServiceGrpc.ProductCommandServiceBlockingStub productCommandService;

    @Autowired
    private ProductQueryServiceGrpc.ProductQueryServiceBlockingStub productQueryService;

    public Integer createProduct(PProduct product) {
        Int32Value res = productCommandService.createProduct(product);
        return res.getValue();
    }

    public Integer updateProduct(PProduct product) {
        Int32Value res = productCommandService.updateProduct(product);
        return res.getValue();
    }

    public PProduct getProductsById(Integer productId) {
        return productQueryService.getProductsById(Int32Value.of(productId));
    }

    public List<PProduct> getProductsByCriteria(PProductCriteriaRequest productCriteriaRequest) {
        PProductsResponse res = productQueryService.getProductsByCriteria(productCriteriaRequest);
        return res.getProductsList();
    }
}
