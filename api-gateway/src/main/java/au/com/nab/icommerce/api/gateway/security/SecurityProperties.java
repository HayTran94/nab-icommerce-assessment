package au.com.nab.icommerce.api.gateway.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.auth")
public class SecurityProperties {
    private String headerName;
    private String headerPrefix;
    private Integer expirationTime;
    private String secretKey;
    private String loginUrl;
}
