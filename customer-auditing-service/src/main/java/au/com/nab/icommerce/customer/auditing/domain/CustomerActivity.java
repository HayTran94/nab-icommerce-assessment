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

    @Field(type = FieldType.Integer, name = "customer_id")
    private Integer customerId;

    @Field(type = FieldType.Keyword, name = "action")
    private String action;

    @Field(type = FieldType.Keyword, name = "method")
    private String method;

    @Field(type = FieldType.Text, name = "request_uri")
    private String requestURI;

    @Field(type = FieldType.Text, name = "query_string")
    private String queryString;

    @Field(type = FieldType.Long, name = "date_time")
    private Long dateTime;

    @Field(type = FieldType.Text, name = "body")
    private String body;

    @Field(type = FieldType.Text, name = "response")
    private String response;

}
