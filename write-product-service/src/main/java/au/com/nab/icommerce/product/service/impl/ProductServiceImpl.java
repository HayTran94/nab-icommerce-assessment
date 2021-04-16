package au.com.nab.icommerce.product.service.impl;

import au.com.nab.icommerce.product.constant.AppConstant;
import au.com.nab.icommerce.product.entity.Product;
import au.com.nab.icommerce.product.mapper.ProductMapper;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.repository.ProductRepository;
import au.com.nab.icommerce.product.service.ProductService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Int32Value createProduct(PProduct request) {
        int res = AppConstant.FAILED;
        try {
            Product product = ProductMapper.toEntity(request);
            product = productRepository.save(product);
            if (product.getId() > 0) {
                res = AppConstant.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int32Value.of(res);
    }

    @Override
    public Int32Value updateProduct(PProduct request) {
        int res = AppConstant.FAILED;
        try {
            Product product = ProductMapper.toEntity(request);
            productRepository.save(product);
            res = AppConstant.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int32Value.of(res);
    }

}
