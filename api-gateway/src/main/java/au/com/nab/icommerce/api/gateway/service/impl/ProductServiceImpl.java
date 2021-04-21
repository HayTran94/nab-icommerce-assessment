package au.com.nab.icommerce.api.gateway.service.impl;

import au.com.nab.icommerce.api.gateway.dto.request.ProductCriteria;
import au.com.nab.icommerce.api.gateway.dto.response.Product;
import au.com.nab.icommerce.api.gateway.mapper.ProductCriteriaMapper;
import au.com.nab.icommerce.api.gateway.mapper.ProductMapper;
import au.com.nab.icommerce.api.gateway.service.ProductService;
import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCommandServiceGrpc.ProductCommandServiceBlockingStub productCommandService;

    @Autowired
    private ProductQueryServiceGrpc.ProductQueryServiceBlockingStub productQueryService;

    @Autowired
    private ProductCriteriaMapper productCriteriaMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Integer productId) {
        PProduct pProduct = productQueryService.getProductsById(Int32Value.of(productId));
        return productMapper.toDomain(pProduct);
    }

    @Override
    public List<Product> getProductsByCriteria(ProductCriteria productCriteria) {
        PProductCriteria pProductCriteria = productCriteriaMapper.toProtobuf(productCriteria);
        PProductsResponse productsResponse = productQueryService.getProductsByCriteria(pProductCriteria);
        List<PProduct> pProducts = productsResponse.getProductsList();
        return productMapper.toDomainList(pProducts);
    }

    @Override
    public Integer createProduct(Product product) {
        PProduct pProduct = productMapper.toProtobuf(product);
        Int32Value res = productCommandService.createProduct(pProduct);
        return res.getValue();
    }

    @Override
    public Integer updateProduct(Product product) {
        PProduct pProduct = productMapper.toProtobuf(product);
        Int32Value res = productCommandService.updateProduct(pProduct);
        return res.getValue();
    }
}
