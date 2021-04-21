package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemRequest {
    @NotNull
    @Min(1)
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
