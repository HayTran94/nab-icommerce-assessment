package au.com.nab.icommerce.cart.cache;

public class CacheKeyManager {

    public static String getCartCacheKey(int customerId) {
        return String.format("customer:%s", customerId);
    }
}
