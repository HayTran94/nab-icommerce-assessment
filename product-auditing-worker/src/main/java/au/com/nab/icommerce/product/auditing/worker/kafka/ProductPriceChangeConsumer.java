package au.com.nab.icommerce.product.auditing.worker.kafka;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.product.auditing.worker.client.ProductAuditingServiceClient;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCMessage;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCPayload;
import au.com.nab.icommerce.product.auditing.worker.domain.CDCProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductPriceChangeConsumer {

    @Autowired
    private ProductAuditingServiceClient productAuditingServiceClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "cdc-product")
    public void cdcProductListener(String message) {
        try {
            CDCMessage cdcMessage = objectMapper.readValue(message, CDCMessage.class);
            CDCPayload cdcPayload = cdcMessage.getPayload();
            CDCProduct productBefore = cdcPayload.getBefore();
            CDCProduct productAfter = cdcPayload.getAfter();

            int oldPrice = 0;
            int newPrice = 0;
            int productId = 0;

            if (productBefore != null) {
                productId = productBefore.getId();
                oldPrice = productBefore.getPrice();
            }

            if (productAfter != null) {
                productId = productAfter.getId();
                newPrice = productAfter.getPrice();
            }

            if (oldPrice == newPrice) {
                return;
            }

            PProductPriceHistory productPriceHistory = PProductPriceHistory.newBuilder()
                    .setProductId(productId)
                    .setOldPrice(oldPrice)
                    .setNewPrice(newPrice)
                    .setDateTime(System.currentTimeMillis())
                    .build();
            int response = productAuditingServiceClient.addProductPriceHistory(productPriceHistory);
            if (ErrorCodeHelper.isFail(response)) {
                log.error("FAILED: productAuditingService.addProductPriceHistory({}) => {}", productPriceHistory, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
