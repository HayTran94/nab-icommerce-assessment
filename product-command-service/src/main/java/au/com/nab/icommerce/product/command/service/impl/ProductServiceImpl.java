package au.com.nab.icommerce.product.command.service.impl;

import au.com.nab.icommerce.product.command.constant.AppConstant;
import au.com.nab.icommerce.product.command.domain.Product;
import au.com.nab.icommerce.product.command.mapper.ProductMapper;
import au.com.nab.icommerce.product.command.repository.ProductRepository;
import au.com.nab.icommerce.product.command.service.ProductService;
import au.com.nab.icommerce.product.protobuf.PProduct;
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
    public Int32Value createProduct(PProduct pProduct) {
        int res = AppConstant.FAILED;
        try {
            Product product = ProductMapper.toDomain(pProduct);
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
    public Int32Value updateProduct(PProduct pProduct) {
        int res = AppConstant.FAILED;
        try {
            Product product = ProductMapper.toDomain(pProduct);
            productRepository.save(product);
            res = AppConstant.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int32Value.of(res);
    }

}