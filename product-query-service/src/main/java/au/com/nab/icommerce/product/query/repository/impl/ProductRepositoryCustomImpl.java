package au.com.nab.icommerce.product.query.repository.impl;

import au.com.nab.icommerce.product.query.constant.AppConstant;
import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductSearchCriteria;
import au.com.nab.icommerce.product.query.repository.ProductRepositoryCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private static final String PRODUCT_INDEX = "product";

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public List<Product> findProductsByCriteria(ProductSearchCriteria criteria) {
        Criteria esCriteria = new Criteria();

        if (StringUtils.isNotBlank(criteria.getName())) {
            esCriteria.and(Criteria.where("name").contains(criteria.getName()));
        }

        if (StringUtils.isNotBlank(criteria.getBrand())) {
            esCriteria.and(Criteria.where("brand").contains(criteria.getBrand()));
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

        int offset = 0;
        if (criteria.getOffset() > 0) {
            offset = criteria.getOffset();
        }
        int limit = AppConstant.DEFAULT_SEARCH_SIZE;
        if (criteria.getLimit() > 0) {
            limit = criteria.getLimit();
        }


//        CriteriaQuery query = new CriteriaQuery(esCriteria).setPageable(page);
//
//        elasticsearchTemplate.sea(query, ClothingArticle.class)
        return null;
    }

}
