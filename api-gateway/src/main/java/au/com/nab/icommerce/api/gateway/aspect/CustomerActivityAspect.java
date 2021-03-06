package au.com.nab.icommerce.api.gateway.aspect;

import au.com.nab.icommerce.api.gateway.security.SecurityContextHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class CustomerActivityAspect {

    @Autowired
    private KafkaTemplate<Integer, Object> kafkaTemplate;

    private static final String KAFKA_TOPIC = "customer-activity";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterReturning(pointcut = "execution(@au.com.nab.icommerce.api.gateway.aspect.CustomerActivity * *(..)) && @annotation(customerActivity)",
            returning = "response", argNames = "joinPoint,customerActivity,response")
    public void auditInfo(JoinPoint joinPoint, CustomerActivity customerActivity, Object response) {
        try {
            PCustomer customer = SecurityContextHelper.getLoggedInCustomer();
            // Customer authentication failed
            if (customer == PCustomer.getDefaultInstance()) {
                return;
            }

            String action = customerActivity.value();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String method = request.getMethod();
            String requestURI = request.getRequestURI();
            String queryString = StringUtils.defaultString(request.getQueryString());
            String bodyJson = new String(((ContentCachingRequestWrapper) request).getContentAsByteArray());
            if (StringUtils.isNotBlank(bodyJson)) {
                JsonNode jsonNode = objectMapper.readValue(bodyJson, JsonNode.class);
                bodyJson = jsonNode.toString();
            }
            String responseJSon = objectMapper.writeValueAsString(response);

            CustomerActivityData customerActivityData = CustomerActivityData.builder()
                    .customerId(customer.getId())
                    .action(action)
                    .method(method)
                    .requestURI(requestURI)
                    .queryString(queryString)
                    .dateTime(System.currentTimeMillis())
                    .body(bodyJson)
                    .response(responseJSon)
                    .build();

            // Start new thread to avoid blocking the main thread
            Runnable runnable = () -> {
                ListenableFuture<SendResult<Integer, Object>> future = kafkaTemplate.send(KAFKA_TOPIC, customer.getId(), customerActivityData);
                future.addCallback(sendResult -> {
                }, throwable -> log.error(throwable.getMessage(), throwable));
            };

            new Thread(runnable).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
