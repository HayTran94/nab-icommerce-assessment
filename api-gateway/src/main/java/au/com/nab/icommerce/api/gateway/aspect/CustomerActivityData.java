package au.com.nab.icommerce.api.gateway.aspect;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivityData {
    private String action;
    private String method;
    private String requestURI;
    private String queryString;
    private Object body;
    private Object response;
    private Long dataTime;
}
