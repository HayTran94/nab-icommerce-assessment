package au.com.nab.icommerce.product.auditing.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@Setter
@Document(indexName = "product-price-history")
public class ProductPriceHistory {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer, name = "product_id")
    private Integer productId;

    @Field(type = FieldType.Integer, name = "old_price")
    private Integer oldPrice;

    @Field(type = FieldType.Integer, name = "new_price")
    private Integer newPrice;

    @Field(type = FieldType.Long, name = "date_time")
    private Long dateTime;

}
