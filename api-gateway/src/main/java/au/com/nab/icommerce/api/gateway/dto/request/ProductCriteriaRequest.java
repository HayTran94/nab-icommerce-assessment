package au.com.nab.icommerce.api.gateway.dto.request;

import au.com.nab.icommerce.protobuf.domain.Paging;
import au.com.nab.icommerce.protobuf.domain.Sorting;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductCriteriaRequest {
    private String name;

    private String brand;

    @Min(0)
    private int priceFrom;

    @Min(0)
    private int priceTo;

    private String color;

    private String unit;

    @NotNull
    private Paging paging;

    private Sorting sorting;
}
