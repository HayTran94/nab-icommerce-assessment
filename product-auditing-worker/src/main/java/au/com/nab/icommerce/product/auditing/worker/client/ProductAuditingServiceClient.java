package au.com.nab.icommerce.product.auditing.worker.client;

import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductAuditingServiceClient {

    @Autowired
    private ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub productAuditingServiceBlockingStub;

    public int addProductPriceHistory(PProductPriceHistory pProductPriceHistory) {
        Int32Value response = productAuditingServiceBlockingStub.addProductPriceHistory(pProductPriceHistory);
        return response.getValue();
    }
}
