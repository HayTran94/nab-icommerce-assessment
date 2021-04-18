package au.com.nab.icommerce.product.query.mapper;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.stream.Collectors;

public class SearchHitsMapper {
    public static <T> List<T> toDomain(SearchHits<T> searchHits) {
        return searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
