package au.com.nab.icommerce.product.query.repository.impl;

import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;
import au.com.nab.icommerce.product.query.mapper.SearchHitsMapper;
import au.com.nab.icommerce.product.query.repository.ProductRepositoryCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static au.com.nab.icommerce.product.query.constant.AppConstant.DEFAULT_PAGE_INDEX;
import static au.com.nab.icommerce.product.query.constant.AppConstant.DEFAULT_PAGE_SIZE;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public List<Product> findProductsByCriteria(ProductCriteria criteria) {
        Criteria esCriteria = new Criteria();

        if (StringUtils.isNotBlank(criteria.getName())) {
            esCriteria.and(Criteria.where("name").expression(criteria.getName()));
        }

        if (StringUtils.isNotBlank(criteria.getBrand())) {
            esCriteria.and(Criteria.where("brand").expression(criteria.getBrand()));
        }

        if (criteria.getPriceFrom() > 0) {
            esCriteria.and(Criteria.where("price").greaterThanEqual(criteria.getPriceFrom()));
        }

        if (criteria.getPriceTo() > 0) {
            esCriteria.and(Criteria.where("price").lessThanEqual(criteria.getPriceTo()));
        }

        if (StringUtils.isNotBlank(criteria.getColor())) {
            esCriteria.and(Criteria.where("color").is(criteria.getColor()));
        }

        if (StringUtils.isNotBlank(criteria.getUnit())) {
            esCriteria.and(Criteria.where("unit").is(criteria.getUnit()));
        }

        int pageIndex = DEFAULT_PAGE_INDEX;
        if (criteria.getPageIndex() > 0) {
            pageIndex = criteria.getPageIndex();
        }
        int pageSize = DEFAULT_PAGE_SIZE;
        if (criteria.getPageSize() > 0) {
            pageSize = criteria.getPageSize();
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        CriteriaQuery query = new CriteriaQuery(esCriteria, pageable);
        SearchHits<Product> searchHits = elasticsearchTemplate.search(query, Product.class);
        return SearchHitsMapper.toDomain(searchHits);
    }

}
