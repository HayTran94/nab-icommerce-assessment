package au.com.nab.icommerce.order.helper;

import au.com.nab.icommerce.order.domain.OrderStatus;

import static au.com.nab.icommerce.order.domain.OrderStatus.*;

public class OrderStatusHelper {
    public static boolean isChangeStatusValid(OrderStatus oldStatus, OrderStatus newStatus) {
        if (oldStatus == newStatus) {
            return false;
        }

        switch (oldStatus) {
            case UNKNOWN:
            case INIT:
                return newStatus == PROCESSING;

            case PROCESSING:
                return newStatus == DELIVERING;

            case DELIVERING:
                return newStatus == COMPLETED || newStatus == DROP_OFF_FAIL;
        }

        return false;
    }
}
