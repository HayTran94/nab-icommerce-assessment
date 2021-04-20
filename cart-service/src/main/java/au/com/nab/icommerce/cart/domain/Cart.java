package au.com.nab.icommerce.cart.domain;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RedisHash(value = "cart", timeToLive = 2592000)    // 30 days
public class Cart implements Serializable {

    private String id;
    private Integer customerId;
    private List<Item> items = new ArrayList<>();

}
