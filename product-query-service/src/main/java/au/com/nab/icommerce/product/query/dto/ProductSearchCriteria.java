package au.com.nab.icommerce.product.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearchCriteria {
    private String name;
    private String brand;
    private int priceFrom;
    private int priceTo;
    private String color;
    private String unit;
    private int offset;
    private int limit;
}
