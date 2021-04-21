package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponse {
    private Integer customerId;
    private List<CartItemResponse> items;
}
