package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.AddToCartRequest;
import au.com.nab.icommerce.api.gateway.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ApiMessage addItemsToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        return cartService.addItemsToCart(addToCartRequest);
    }

    @PostMapping("/customer/{customerId}")
    public ApiMessage getCustomerCart(@PathVariable Integer customerId) {
        return cartService.getCustomerCart(customerId);
    }

    @DeleteMapping("/customer/{customerId}")
    public ApiMessage createProduct(@PathVariable Integer customerId) {
        return cartService.clearCustomerCart(customerId);
    }

}
