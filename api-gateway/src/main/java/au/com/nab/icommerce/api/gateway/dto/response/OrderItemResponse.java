package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer price;
    private Integer quantity;
}
