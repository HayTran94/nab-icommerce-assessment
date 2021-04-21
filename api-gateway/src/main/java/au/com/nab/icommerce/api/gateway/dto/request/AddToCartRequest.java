package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AddToCartRequest {
    @Min(1)
    @NotNull
    private Integer customerId;

    @NotEmpty
    private List<CartItemRequest> items;
}
