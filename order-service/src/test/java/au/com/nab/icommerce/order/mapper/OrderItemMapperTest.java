package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.OrderItem;
import au.com.nab.icommerce.order.protobuf.POrderItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderItemMapperTest {
    private final OrderItemMapper orderItemMapper = OrderItemMapper.INSTANCE;

    @Test
    public void shouldSuccessToDomainWhenValidData() {
        // GIVEN
        POrderItem pOrderItem = POrderItem.newBuilder()
                .setId(1)
                .setProductId(2)
                .setPrice(3)
                .setQuantity(4)
                .build();

        // WHEN
        OrderItem orderItem = orderItemMapper.toDomain(pOrderItem);

        // THEN
        Assert.assertNotNull(orderItem);
        Assert.assertEquals(1, (int) orderItem.getId());
        Assert.assertEquals(2, (int) orderItem.getProductId());
        Assert.assertEquals(3, (int) orderItem.getPrice());
        Assert.assertEquals(4, (int) orderItem.getQuantity());
    }

    @Test
    public void shouldSuccessToDomainWhenInputIsNull() {
        // WHEN
        OrderItem orderItem = orderItemMapper.toDomain(null);

        // THEN
        Assert.assertNull(orderItem);
    }

    @Test
    public void shouldSuccessToProtobufWhenValidData() {
        // GIVEN
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProductId(2);
        orderItem.setPrice(3);
        orderItem.setQuantity(4);

        // WHEN
        POrderItem pOrderItem = orderItemMapper.toProtobuf(orderItem);

        // THEN
        Assert.assertNotNull(pOrderItem);
        Assert.assertEquals(1, pOrderItem.getId());
        Assert.assertEquals(2, pOrderItem.getProductId());
        Assert.assertEquals(3, pOrderItem.getPrice());
        Assert.assertEquals(4, pOrderItem.getQuantity());
    }

    @Test
    public void shouldSuccessToProtobufWhenInputIsNull() {
        // WHEN
        POrderItem pOrderItem = orderItemMapper.toProtobuf(null);

        // THEN
        Assert.assertNull(pOrderItem);
    }
}