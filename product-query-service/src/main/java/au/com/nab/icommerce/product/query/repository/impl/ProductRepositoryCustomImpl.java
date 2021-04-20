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
    public List<Product> findProductsByCriteria(ProductCriteria productCriteria) {
        Criteria esCriteria = new Criteria();

        if (StringUtils.isNotBlank(productCriteria.getName())) {
            esCriteria.and(Criteria.where("name").expression(productCriteria.getName()));
        }

        if (StringUtils.isNotBlank(productCriteria.getBrand())) {
            esCriteria.and(Criteria.where("brand").expression(productCriteria.getBrand()));
        }

        if (productCriteria.getPriceFrom() > 0) {
            esCriteria.and(Criteria.where("price").greaterThanEqual(productCriteria.getPriceFrom()));
        }

        if (productCriteria.getPriceTo() > 0) {
            esCriteria.and(Criteria.where("price").lessThanEqual(productCriteria.getPriceTo()));
        }

        if (StringUtils.isNotBlank(productCriteria.getColor())) {
            esCriteria.and(Criteria.where("color").is(productCriteria.getColor()));
        }

        if (StringUtils.isNotBlank(productCriteria.getUnit())) {
            esCriteria.and(Criteria.where("unit").is(productCriteria.getUnit()));
        }

        int pageIndex = DEFAULT_PAGE_INDEX;
        if (productCriteria.getPageIndex() > 0) {
            pageIndex = productCriteria.getPageIndex();
        }
        int pageSize = DEFAULT_PAGE_SIZE;
        if (productCriteria.getPageSize() > 0) {
            pageSize = productCriteria.getPageSize();
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        CriteriaQuery query = new CriteriaQuery(esCriteria, pageable);
        SearchHits<Product> searchHits = elasticsearchTemplate.search(query, Product.class);
        return SearchHitsMapper.toDomain(searchHits);
    }

}
