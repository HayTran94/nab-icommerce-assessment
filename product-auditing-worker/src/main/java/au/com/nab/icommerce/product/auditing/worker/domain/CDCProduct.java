package au.com.nab.icommerce.product.auditing.worker.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDCProduct {
    private Integer id;
    private String name;
    private String brand;
    private Integer price;
    private String color;
    private String image;
    private String unit;
}
