package au.com.nab.icommerce.protobuf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paging {
    public static final int DEFAULT_INDEX = 0;
    public static final int DEFAULT_SIZE = 100;
    public static final Paging DEFAULT = Paging.of(DEFAULT_INDEX, DEFAULT_SIZE);

    private int index;
    private int size;

    public static Paging of(int index, int size) {
        return new Paging(index, size);
    }

}
