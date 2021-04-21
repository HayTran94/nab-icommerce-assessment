package au.com.nab.icommerce.api.gateway.security;

import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {
    public static PCustomer getCustomer() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return PCustomer.getDefaultInstance();
        }

        PCustomer customer = (PCustomer) authentication.getDetails();
        if (customer == null) {
            customer = PCustomer.getDefaultInstance();
        }
        return customer;
    }
}
