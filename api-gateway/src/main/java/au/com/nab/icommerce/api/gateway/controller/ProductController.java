package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.ProductCriteriaRequest;
import au.com.nab.icommerce.api.gateway.dto.ProductRequest;
import au.com.nab.icommerce.api.gateway.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ApiMessage getProduct(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/search")
    public ApiMessage searchProducts(@RequestBody ProductCriteriaRequest productCriteriaRequest) {
        return productService.getProductsByCriteria(productCriteriaRequest);
    }

    @PostMapping
    public ApiMessage createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping
    public ApiMessage updateProduct(@RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productRequest);
    }

}
