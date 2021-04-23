package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.PrepareData;
import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.protobuf.POrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderMapperTest {
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    public void shouldSuccessToDomainWhenValidData() {
        // GIVEN
        POrder pOrder = PrepareData.preparePersistentPOrder();

        // WHEN
        Order order = orderMapper.toDomain(pOrder);

        // THEN
        Assert.assertNotNull(order);
        Assert.assertEquals(PrepareData.ORDER_ID, (int) order.getId());
        Assert.assertEquals(123, (int) order.getCustomerId());
        Assert.assertEquals("Vo Thanh Tai", order.getCustomerName());
        Assert.assertEquals(10000000, (int) order.getTotalAmount());
        Assert.assertEquals(2, order.getItems().size());
    }

    @Test
    public void shouldSuccessToDomainWhenInputIsNull() {
        // WHEN
        Order order = orderMapper.toDomain(null);

        // THEN
        Assert.assertNull(order);
    }

    @Test
    public void shouldSuccessToProtobufWhenValidData() {
        // GIVEN
        Order order = PrepareData.preparePersistentOrder();

        // WHEN
        POrder pOrder = orderMapper.toProtobuf(order);

        // THEN
        Assert.assertNotNull(pOrder);
        Assert.assertEquals(PrepareData.ORDER_ID, pOrder.getId());
        Assert.assertEquals(123, pOrder.getCustomerId());
        Assert.assertEquals("Vo Thanh Tai", pOrder.getCustomerName());
        Assert.assertEquals(10000000, pOrder.getTotalAmount());
        Assert.assertEquals(2, pOrder.getItemsCount());
    }

    @Test
    public void shouldSuccessToProtobufWhenInputIsNull() {
        // WHEN
        POrder pOrder = orderMapper.toProtobuf(null);

        // THEN
        Assert.assertNull(pOrder);
    }
}