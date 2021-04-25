package au.com.nab.icommerce.api.gateway.security;

import au.com.nab.icommerce.customer.protobuf.PCustomer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {
    public static PCustomer getLoggedInCustomer() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return PCustomer.getDefaultInstance();
        }

        Object authenticationDetails = authentication.getDetails();
        if (!(authenticationDetails instanceof PCustomer)) {
            return PCustomer.getDefaultInstance();
        }

        return (PCustomer) authenticationDetails;
    }
}
