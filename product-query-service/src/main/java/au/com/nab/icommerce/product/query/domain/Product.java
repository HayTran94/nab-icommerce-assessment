package au.com.nab.icommerce.product.query.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "cdc.nab_product.product")
public class Product {

    @Id
    private Integer id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "brand")
    private String brand;

    @Field(type = FieldType.Integer, name = "price")
    private Integer price;

    @Field(type = FieldType.Text, name = "color")
    private String color;

    @Field(type = FieldType.Text, name = "image")
    private String image;

    @Field(type = FieldType.Text, name = "unit")
    private String unit;

}
