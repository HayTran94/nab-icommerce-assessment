package au.com.nab.icommerce.product.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCriteria {
    private String name;
    private String brand;
    private int priceFrom;
    private int priceTo;
    private String color;
    private String unit;
    private int pageIndex;
    private int pageSize;
}
