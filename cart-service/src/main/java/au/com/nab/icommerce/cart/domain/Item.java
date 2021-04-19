package au.com.nab.icommerce.cart.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash
public class Item {
    private Integer productId;
    private Integer quantity;
    private Long dateTime;
}
