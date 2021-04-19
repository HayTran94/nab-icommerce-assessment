package au.com.nab.icommerce.common.mapper;

import com.google.protobuf.GeneratedMessageV3;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractProtobufMapper<E, P extends GeneratedMessageV3> {

    public abstract E toDomain(P protobuf);

    public List<E> toDomain(List<P> protobufs) {
        if (protobufs == null) {
            return Collections.emptyList();
        }

        return protobufs.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public abstract P toProtobuf(E entity);

    public List<P> toProtobuf(List<E> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(this::toProtobuf)
                .collect(Collectors.toList());
    }
}
