package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.ProductCriteria;
import au.com.nab.icommerce.api.gateway.dto.response.Product;
import au.com.nab.icommerce.api.gateway.service.ProductService;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ApiMessage getProduct(@PathVariable Integer productId) {
        try {
            Product product = productService.getProductById(productId);
            return ApiMessage.success(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping("/search")
    public ApiMessage searchProducts(@RequestBody ProductCriteria productCriteria) {
        try {
            List<Product> products = productService.getProductsByCriteria(productCriteria);
            return ApiMessage.success(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping
    public ApiMessage createProduct(@RequestBody Product product) {
        try {
            Integer res = productService.createProduct(product);
            if (ErrorCodeHelper.isFail(res)) {
                return ApiMessage.FAILED;
            }

            return ApiMessage.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateProduct(@RequestBody Product product) {
        try {
            Integer res = productService.updateProduct(product);
            if (ErrorCodeHelper.isFail(res)) {
                return ApiMessage.FAILED;
            }

            return ApiMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
