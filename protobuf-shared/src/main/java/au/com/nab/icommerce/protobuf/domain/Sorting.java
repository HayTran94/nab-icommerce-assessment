package au.com.nab.icommerce.protobuf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sorting {
    private String property;
    private Direction direction;

    public static Sorting of(String property, Direction direction) {
        return new Sorting(property, direction);
    }
}
