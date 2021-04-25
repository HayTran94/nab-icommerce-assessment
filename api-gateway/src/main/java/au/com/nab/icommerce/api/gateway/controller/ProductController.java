package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.aspect.CustomerActivity;
import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.ProductCriteriaRequest;
import au.com.nab.icommerce.api.gateway.dto.request.ProductRequest;
import au.com.nab.icommerce.api.gateway.dto.response.ProductPriceHistoryResponse;
import au.com.nab.icommerce.api.gateway.mapper.request.ProductCriteriaRequestMapper;
import au.com.nab.icommerce.api.gateway.mapper.request.ProductRequestMapper;
import au.com.nab.icommerce.api.gateway.mapper.response.ProductPriceHistoryResponseMapper;
import au.com.nab.icommerce.api.gateway.mapper.response.ProductResponseMapper;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
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

    private final ProductCriteriaRequestMapper productCriteriaRequestMapper = ProductCriteriaRequestMapper.INSTANCE;
    private final ProductRequestMapper productRequestMapper = ProductRequestMapper.INSTANCE;
    private final ProductResponseMapper productResponseMapper = ProductResponseMapper.INSTANCE;
    private final ProductPriceHistoryResponseMapper productPriceHistoryResponseMapper = ProductPriceHistoryResponseMapper.INSTANCE;

    @GetMapping("/{productId}")
    @CustomerActivity("GET_PRODUCT_INFO")
    public ApiMessage getProductInfo(@PathVariable Integer productId) {
        try {
            PProduct product = productServiceClient.getProductsById(productId);
            if (product == null) {
                return ApiMessage.PRODUCT_NOT_FOUND;
            }

            return ApiMessage.success(productResponseMapper.toDomain(product));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping("/search")
    @CustomerActivity("SEARCH_PRODUCTS")
    public ApiMessage searchProducts(@RequestBody ProductCriteriaRequest productCriteriaRequest) {
        try {
            PProductCriteriaRequest pProductCriteriaRequest = productCriteriaRequestMapper.toProtobuf(productCriteriaRequest);
            List<PProduct> products = productServiceClient.getProductsByCriteria(pProductCriteriaRequest);
            return ApiMessage.success(productResponseMapper.toDomainList(products));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{productId}/price/histories")
    @CustomerActivity("GET_PRODUCT_PRICE_HISTORIES")
    public ApiMessage getProductPriceHistories(@PathVariable Integer productId) {
        try {
            List<PProductPriceHistory> priceHistories = productServiceClient.getProductPriceHistories(productId);
            List<ProductPriceHistoryResponse> priceHistoryResponses = productPriceHistoryResponseMapper.toDomainList(priceHistories);
            return ApiMessage.success(priceHistoryResponses);
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

            PProduct pProduct = productServiceClient.getProductsById(product.getId());
            if (pProduct == null) {
                return ApiMessage.PRODUCT_NOT_FOUND;
            }

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
