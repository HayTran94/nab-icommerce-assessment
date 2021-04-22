package au.com.nab.icommerce.api.gateway.security;

import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.exception.JwtParsingException;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author vothanhtai
 * @since 2/24/20 18:27
 */
@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {
    private CustomerServiceClient customerServiceClient;
    private SecurityProperties securityProperties;

    public AuthenticationFilter(AuthenticationManager authenticationManager, CustomerServiceClient customerServiceClient, SecurityProperties securityProperties) {
        super(authenticationManager);
        this.customerServiceClient = customerServiceClient;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String pathInfo = request.getRequestURI();
        if (securityProperties.getLoginUrl().equals(pathInfo)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Integer customerId = parseJWTToken(request);
            PCustomer customer = customerServiceClient.getCustomerById(customerId);
            Objects.requireNonNull(customer);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(customerId, null, Collections.emptyList());
            authentication.setDetails(customer);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
        }
    }

    private Integer parseJWTToken(HttpServletRequest request) throws AuthenticationException {
        String authorizationHeader = request.getHeader(securityProperties.getHeaderName());
        authorizationHeader = StringUtils.trimWhitespace(authorizationHeader);

        if (StringUtils.isEmpty(authorizationHeader)) {
            log.info("The request does not contain 'Authorization' value in the request headers");
            return null;
        }

        String token = StringUtils.replace(authorizationHeader, securityProperties.getHeaderPrefix(), EMPTY);

        try {
            String customerId = Jwts.parser()
                    .setSigningKey(securityProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return Integer.parseInt(customerId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Error when trying to parse Jwt token");
            throw new JwtParsingException("JWT token is invalid", ex);
        }
    }

}
