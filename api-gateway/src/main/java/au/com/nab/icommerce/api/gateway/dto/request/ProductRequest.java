package au.com.nab.icommerce.api.gateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductRequest {
    @Min(1)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotNull
    @Min(0)
    private Integer price;

    private String color;

    private String image;

    private String unit;
}
