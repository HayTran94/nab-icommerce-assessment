package au.com.nab.icommerce.product.auditing.service;

import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistoriesResponse;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import com.google.protobuf.Int32Value;

public interface ProductPriceHistoryService {
    Int32Value addProductPriceHistory(PProductPriceHistory pProductPriceHistory);

    PProductPriceHistoriesResponse getProductPriceHistories(Int32Value productId);
}
