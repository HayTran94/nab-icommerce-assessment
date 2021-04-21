package au.com.nab.icommerce.api.gateway.service.impl;

import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.ProductCriteriaRequest;
import au.com.nab.icommerce.api.gateway.dto.ProductRequest;
import au.com.nab.icommerce.api.gateway.mapper.ProductCriteriaRequestMapper;
import au.com.nab.icommerce.api.gateway.mapper.ProductRequestMapper;
import au.com.nab.icommerce.api.gateway.service.ProductService;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCommandServiceGrpc.ProductCommandServiceBlockingStub productCommandService;

    @Autowired
    private ProductQueryServiceGrpc.ProductQueryServiceBlockingStub productQueryService;

    @Autowired
    private ProductCriteriaRequestMapper productCriteriaRequestMapper;

    @Autowired
    private ProductRequestMapper productRequestMapper;

    @Override
    public ApiMessage getProductById(Integer productId) {
        try {
            PProduct pProduct = productQueryService.getProductsById(Int32Value.of(productId));
            if (pProduct == null) {
                return ApiMessage.NOT_FOUND;
            }

            return ApiMessage.success(pProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Override
    public ApiMessage getProductsByCriteria(ProductCriteriaRequest productCriteriaRequest) {
        try {
            PProductCriteriaRequest pProductCriteriaRequest = productCriteriaRequestMapper.toProtobuf(productCriteriaRequest);
            PProductsResponse productsResponse = productQueryService.getProductsByCriteria(pProductCriteriaRequest);
            return ApiMessage.success(productsResponse.getProductsList());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Override
    public ApiMessage createProduct(ProductRequest productRequest) {
        try {
            PProduct pProduct = productRequestMapper.toProtobuf(productRequest);
            Int32Value res = productCommandService.createProduct(pProduct);
            if (ErrorCodeHelper.isFail(res.getValue())) {
                return ApiMessage.CREATE_FAILED;
            }

            return ApiMessage.success(res.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Override
    public ApiMessage updateProduct(ProductRequest productRequest) {
        try {
            PProduct pProduct = productRequestMapper.toProtobuf(productRequest);
            Int32Value res = productCommandService.updateProduct(pProduct);
            if (ErrorCodeHelper.isFail(res.getValue())) {
                return ApiMessage.UPDATE_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
