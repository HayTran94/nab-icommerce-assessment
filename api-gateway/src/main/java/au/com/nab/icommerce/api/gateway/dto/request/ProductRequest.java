package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private Integer id;
    private String name;
    private String brand;
    private Integer price;
    private String color;
    private String image;
    private String unit;
}
