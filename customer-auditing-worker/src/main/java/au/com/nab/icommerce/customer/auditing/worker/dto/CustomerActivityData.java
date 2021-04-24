package au.com.nab.icommerce.customer.auditing.worker.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivityData {
    private Integer customerId;
    private String action;
    private String method;
    private String requestURI;
    private String queryString;
    private Long dateTime;
    private String body;
    private String response;
}
