package au.com.nab.icommerce.api.gateway.dto.response;

import au.com.nab.icommerce.order.protobuf.POrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Integer id;
    private Integer customerId;
    private String customerName;
    private Integer totalAmount;
    private POrderStatus status;
    private List<OrderItemResponse> items;
}
