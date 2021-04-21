package au.com.nab.icommerce.product.auditing.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.product.auditing.domain.ProductPriceHistory;
import au.com.nab.icommerce.product.auditing.mapper.ProductPriceHistoryMapper;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistoriesResponse;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.product.auditing.repository.ProductPriceHistoryRepository;
import au.com.nab.icommerce.product.auditing.service.ProductPriceHistoryService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {

    @Autowired
    private ProductPriceHistoryRepository productPriceHistoryRepository;

    @Autowired
    private ProductPriceHistoryMapper productPriceHistoryMapper;

    @Override
    public Int32Value addProductPriceHistory(PProductPriceHistory pProductPriceHistory) {
        int res = ErrorCode.FAILED;
        try {
            ProductPriceHistory product = productPriceHistoryMapper.toDomain(pProductPriceHistory);
            if (product.getDateTime() == 0) {
                product.setDateTime(System.currentTimeMillis());
            }

            productPriceHistoryRepository.save(product);
            res = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public PProductPriceHistoriesResponse getProductPriceHistories(Int32Value productId) {
        List<PProductPriceHistory> pProductPriceHistories = Collections.emptyList();
        try {
            List<ProductPriceHistory> productPriceHistories
                    = productPriceHistoryRepository.findAllByProductId(productId.getValue());
            pProductPriceHistories = productPriceHistoryMapper.toProtobufList(productPriceHistories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PProductPriceHistoriesResponse.newBuilder().addAllData(pProductPriceHistories).build();
    }
}
