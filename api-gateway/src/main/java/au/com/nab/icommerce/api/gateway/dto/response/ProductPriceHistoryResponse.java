package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPriceHistoryResponse {
    private int productId;
    private int oldPrice;
    private int newPrice;
    private long dateTime;
}
