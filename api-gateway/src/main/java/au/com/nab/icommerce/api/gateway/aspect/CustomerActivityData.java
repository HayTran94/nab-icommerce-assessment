package au.com.nab.icommerce.api.gateway.aspect;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivityData {
    private Long dateTime;
    private String action;
    private String method;
    private String requestURI;
    private String queryString;
    private Object body;
    private Object response;
}
