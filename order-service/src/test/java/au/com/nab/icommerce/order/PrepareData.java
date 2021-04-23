package au.com.nab.icommerce.order;

import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.mapper.OrderMapper;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrderItem;
import au.com.nab.icommerce.order.protobuf.POrderStatus;

import java.util.Arrays;
import java.util.List;

public class PrepareData {
    public static final int ORDER_ID = 333;

    public static POrder prepareTransientPOrder() {
        POrderItem pOrderItem1 = POrderItem.newBuilder()
                .setProductId(1)
                .setPrice(1500000)
                .setQuantity(3)
                .build();

        POrderItem pOrderItem2 = POrderItem.newBuilder()
                .setProductId(2)
                .setPrice(5500000)
                .setQuantity(1)
                .build();

        return POrder.newBuilder()
                .setCustomerId(123)
                .setCustomerName("Vo Thanh Tai")
                .setTotalAmount(10000000)
                .setStatus(POrderStatus.INIT)
                .addAllItems(Arrays.asList(pOrderItem1, pOrderItem2))
                .build();
    }

    public static POrder preparePersistentPOrder() {
        POrder pOrder = prepareTransientPOrder();
        POrder.Builder orderBuilder = pOrder.toBuilder();
        orderBuilder.setId(ORDER_ID);
        orderBuilder.getItemsBuilderList().forEach(itemBuilder -> itemBuilder.setOrderId(ORDER_ID));
        return orderBuilder.build();
    }

    public static Order preparePersistentOrder() {
        POrder pOrder = preparePersistentPOrder();
        return OrderMapper.INSTANCE.toDomain(pOrder);
    }

    public static List<Order> preparePersistentOrders() {
        Order order1 = preparePersistentOrder();
        Order order2 = preparePersistentOrder();
        order2.setId(444);

        return Arrays.asList(order1, order2);
    }
}
