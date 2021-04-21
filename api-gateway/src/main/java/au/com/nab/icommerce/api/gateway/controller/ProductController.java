package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.ProductCriteriaRequest;
import au.com.nab.icommerce.api.gateway.dto.ProductRequest;
import au.com.nab.icommerce.api.gateway.mapper.ProductCriteriaRequestMapper;
import au.com.nab.icommerce.api.gateway.mapper.ProductRequestMapper;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteriaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private ProductCriteriaRequestMapper productCriteriaRequestMapper;

    @Autowired
    private ProductRequestMapper productRequestMapper;

    @GetMapping("/{productId}")
    public ApiMessage getProduct(@PathVariable Integer productId) {
        try {
            PProduct product = productServiceClient.getProductsById(productId);
            if (product == null) {
                return ApiMessage.PRODUCT_NOT_FOUND;
            }

            return ApiMessage.success(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping("/search")
    public ApiMessage searchProducts(@RequestBody ProductCriteriaRequest productCriteriaRequest) {
        try {
            PProductCriteriaRequest pProductCriteriaRequest = productCriteriaRequestMapper.toProtobuf(productCriteriaRequest);
            List<PProduct> products = productServiceClient.getProductsByCriteria(pProductCriteriaRequest);
            return ApiMessage.success(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping
    public ApiMessage createProduct(@RequestBody ProductRequest productRequest) {
        try {
            PProduct product = productRequestMapper.toProtobuf(productRequest);
            int response = productServiceClient.createProduct(product);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.CREATE_FAILED;
            }

            return ApiMessage.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateProduct(@RequestBody ProductRequest productRequest) {
        try {
            PProduct product = productRequestMapper.toProtobuf(productRequest);
            int response = productServiceClient.updateProduct(product);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.UPDATE_FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
