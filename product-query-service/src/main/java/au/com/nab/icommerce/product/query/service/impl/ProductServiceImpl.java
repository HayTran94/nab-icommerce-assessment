package au.com.nab.icommerce.product.query.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductResponse;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;
import au.com.nab.icommerce.product.query.mapper.ProductCriteriaMapper;
import au.com.nab.icommerce.product.query.mapper.ProductMapper;
import au.com.nab.icommerce.product.query.repository.ProductRepository;
import au.com.nab.icommerce.product.query.service.ProductService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCriteriaMapper productCriteriaMapper;

    @Override
    public PProductResponse getProductById(Int32Value id) {
        PProductResponse.Builder response = PProductResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            Optional<Product> product = productRepository.findById(id.getValue());
            if (product.isPresent()) {
                PProduct pProduct = productMapper.toProtobuf(product.get());
                return response.setCode(ErrorCode.SUCCESS).setData(pProduct).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

    @Override
    public PProductsResponse getProductsByCriteria(PProductCriteriaRequest criteria) {
        PProductsResponse.Builder response = PProductsResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            ProductCriteria productCriteria = productCriteriaMapper.toDomain(criteria);
            List<Product> products = productRepository.findProductsByCriteria(productCriteria);
            List<PProduct> pProducts = productMapper.toProtobufList(products);
            return response.setCode(ErrorCode.SUCCESS).addAllData(pProducts).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

}
