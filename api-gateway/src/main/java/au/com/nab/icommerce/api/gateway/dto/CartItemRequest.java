package au.com.nab.icommerce.api.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Integer productId;
    private Integer quantity;
}
