package au.com.nab.icommerce.protobuf.util;

import au.com.nab.icommerce.protobuf.domain.Direction;
import au.com.nab.icommerce.protobuf.domain.Paging;
import au.com.nab.icommerce.protobuf.domain.Sorting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static au.com.nab.icommerce.protobuf.domain.Paging.DEFAULT_INDEX;
import static au.com.nab.icommerce.protobuf.domain.Paging.DEFAULT_SIZE;

public class PagingUtil {
    public static Sort toSpringDataSort(Sorting sorting) {
        if (sorting == null || StringUtils.isBlank(sorting.getProperty()) || sorting.getDirection() == null) {
            return Sort.unsorted();
        }

        Sort sort = Sort.by(sorting.getProperty());
        if (sorting.getDirection() == Direction.ASC) {
            sort = sort.ascending();
        } else if (sorting.getDirection() == Direction.DESC) {
            sort = sort.descending();
        }

        return sort;
    }

    public static Pageable toPageable(Paging paging) {
        return toPageable(paging, null);
    }

    public static Pageable toPageable(Paging paging, Sorting sorting) {
        Sort sort = toSpringDataSort(sorting);
        if (paging == null) {
            return PageRequest.of(DEFAULT_INDEX, DEFAULT_SIZE, sort);
        }

        if (paging.getIndex() < 0) {
            paging.setIndex(0);
        }

        if (paging.getSize() <= 0) {
            paging.setSize(DEFAULT_SIZE);
        }

        return PageRequest.of(paging.getIndex(), paging.getSize(), sort);
    }
}
