package au.com.nab.icommerce.product.query.repository.impl;

import au.com.nab.icommerce.product.query.domain.Product;
import au.com.nab.icommerce.product.query.dto.ProductCriteria;
import au.com.nab.icommerce.product.query.mapper.SearchHitsMapper;
import au.com.nab.icommerce.product.query.repository.ProductRepositoryCustom;
import au.com.nab.icommerce.product.query.util.ESDocumentUtil;
import au.com.nab.icommerce.protobuf.domain.Sorting;
import au.com.nab.icommerce.protobuf.util.PagingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public List<Product> findProductsByCriteria(ProductCriteria productCriteria) {
        Criteria esCriteria = new Criteria();

        if (StringUtils.isNotBlank(productCriteria.getName())) {
            esCriteria.and(Criteria.where("name").contains(productCriteria.getName()));
        }

        if (StringUtils.isNotBlank(productCriteria.getBrand())) {
            esCriteria.and(Criteria.where("brand").contains(productCriteria.getBrand()));
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

        // Check sort field
        Sorting sorting = productCriteria.getSorting();
        if (sorting != null) {
            FieldType fieldType = ESDocumentUtil.getFieldType(Product.class, sorting.getProperty());
            if (FieldType.Text == fieldType) {
                sorting.setProperty(String.format("%s.keyword", sorting.getProperty()));
            }
        }

        Pageable pageable = PagingUtil.toPageable(productCriteria.getPaging(), sorting);
        CriteriaQuery query = new CriteriaQuery(esCriteria, pageable);
        SearchHits<Product> searchHits = elasticsearchTemplate.search(query, Product.class);
        return SearchHitsMapper.toDomain(searchHits);
    }

}
