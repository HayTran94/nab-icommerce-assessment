package au.com.nab.icommerce.product.auditing.worker.kafka;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCMessage;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCPayload;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCProduct;
import com.google.protobuf.Int32Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductPriceChangeConsumer {

    @Autowired
    private ProductAuditingServiceGrpc.ProductAuditingServiceBlockingStub productAuditingService;

    @KafkaListener(topics = "cdc-product", containerFactory = "cdcProductKafkaListenerContainerFactory")
    public void cdcProductListener(CDCMessage cdcMessage) {
        try {
            CDCPayload cdcPayload = cdcMessage.getPayload();
            CDCProduct productBefore = cdcPayload.getBefore();
            CDCProduct productAfter = cdcPayload.getAfter();

            if (productBefore.getPrice().equals(productAfter.getPrice())) {
                return;
            }

            PProductPriceHistory productPriceHistory = PProductPriceHistory.newBuilder()
                    .build();
            Int32Value response = productAuditingService.addProductPriceHistory(productPriceHistory);
            if (ErrorCodeHelper.isFail(response)) {
                log.error("FAILED: productAuditingService.addProductPriceHistory({}) => {}", productPriceHistory, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
