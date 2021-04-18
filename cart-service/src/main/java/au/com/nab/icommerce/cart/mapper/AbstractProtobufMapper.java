package au.com.nab.icommerce.cart.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractProtobufMapper<E, P> {

    public abstract E toDomain(P protobuf);

    public List<E> toDomain(List<P> protobuf) {
        return protobuf.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public abstract P toProtobuf(E entity);

    public List<P> toProtobuf(List<E> entity) {
        return entity.stream()
                .map(this::toProtobuf)
                .collect(Collectors.toList());
    }
}
