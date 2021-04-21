package au.com.nab.icommerce.api.gateway.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Integer id;
    private String name;
    private String brand;
    private Integer price;
    private String color;
    private String image;
    private String unit;
}
