package au.com.nab.icommerce.customer.auditing.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@Setter
@Document(indexName = "customer-activity")
public class CustomerActivity {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer, name = "customerId")
    private Integer customerId;

    @Field(type = FieldType.Keyword, name = "action")
    private String action;

    @Field(type = FieldType.Long, name = "time")
    private Long time;

    @Field(type = FieldType.Text, name = "data")
    private String data;

}
