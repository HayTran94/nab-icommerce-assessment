package au.com.nab.icommerce.protobuf.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ProtobufMapper<D, P> {

    D toDomain(P protobuf);

    P toProtobuf(D domain);

    default List<D> toDomainList(List<P> protobufs) {
        if (protobufs == null) {
            return Collections.emptyList();
        }

        return protobufs.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    default List<P> toProtobufList(List<D> domains) {
        if (domains == null) {
            return Collections.emptyList();
        }

        return domains.stream()
                .map(this::toProtobuf)
                .collect(Collectors.toList());
    }
}
