package au.com.nab.icommerce.product.query.dto;

import au.com.nab.icommerce.protobuf.domain.Paging;
import au.com.nab.icommerce.protobuf.domain.Sorting;
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
    private Paging paging;
    private Sorting sorting;
}
