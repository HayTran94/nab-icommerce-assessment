package au.com.nab.icommerce.api.gateway.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtParsingException extends AuthenticationException {
    public JwtParsingException(String msg, Throwable t) {
        super(msg, t);
    }
}
