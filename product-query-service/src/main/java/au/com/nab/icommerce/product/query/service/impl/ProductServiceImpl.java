package au.com.nab.icommerce.product.query.service.impl;

import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.protobuf.PProductListResponse;
import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;
import au.com.nab.icommerce.product.query.mapper.ProductCriteriaMapper;
import au.com.nab.icommerce.product.query.mapper.ProductMapper;
import au.com.nab.icommerce.product.query.repository.ProductRepository;
import au.com.nab.icommerce.product.query.service.ProductService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public PProduct getProductById(Int32Value id) {
        PProduct pProduct = null;
        try {
            Optional<Product> product = productRepository.findById(id.getValue());
            if (product.isPresent()) {
                pProduct = ProductMapper.toProtobuf(product.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pProduct;
    }

    @Override
    public PProductListResponse getProductsByCriteria(PProductCriteria criteria) {
        List<PProduct> pProducts = Collections.emptyList();
        try {
            ProductCriteria productCriteria = ProductCriteriaMapper.toDto(criteria);
            List<Product> products = productRepository.findProductsByCriteria(productCriteria);
            pProducts = ProductMapper.toProtobuf(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PProductListResponse.newBuilder().addAllProducts(pProducts).build();
    }

}
