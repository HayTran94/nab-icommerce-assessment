package au.com.nab.icommerce.api.gateway.security;

import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class SocialAuthenticationToken extends AbstractAuthenticationToken {
    private final PCustomer customer;

    public SocialAuthenticationToken(PCustomer customer) {
        super(Collections.emptyList());
        if (customer == null) {
            customer = PCustomer.getDefaultInstance();
        }
        this.customer = customer;
        this.setDetails(this.customer);
        this.setAuthenticated(this.customer != PCustomer.getDefaultInstance());
    }

    @Override
    public Object getCredentials() {
        return customer.getToken();
    }

    @Override
    public Object getPrincipal() {
        return customer.getProvider() + "|" + customer.getProviderId();
    }
}
