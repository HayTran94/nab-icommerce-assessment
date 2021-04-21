package au.com.nab.icommerce.api.gateway.dto.request;

import au.com.nab.icommerce.order.protobuf.POrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    @NotNull
    @Min(1)
    private Integer orderId;

    @NotNull
    private POrderStatus status;
}
