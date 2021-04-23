package au.com.nab.icommerce.order.mapper;

import au.com.nab.icommerce.order.domain.OrderStatus;
import au.com.nab.icommerce.order.protobuf.POrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderStatusMapperTest {
    private final OrderStatusMapper orderStatusMapper = OrderStatusMapper.INSTANCE;

    @Test
    public void shouldSuccessToDomainWhenValidData() {
        Assert.assertNull(orderStatusMapper.toDomain(null));
        Assert.assertNull(orderStatusMapper.toDomain(POrderStatus.UNRECOGNIZED));
        Assert.assertEquals(OrderStatus.UNKNOWN, orderStatusMapper.toDomain(POrderStatus.UNKNOWN));
        Assert.assertEquals(OrderStatus.INIT, orderStatusMapper.toDomain(POrderStatus.INIT));
        Assert.assertEquals(OrderStatus.PROCESSING, orderStatusMapper.toDomain(POrderStatus.PROCESSING));
        Assert.assertEquals(OrderStatus.DELIVERING, orderStatusMapper.toDomain(POrderStatus.DELIVERING));
        Assert.assertEquals(OrderStatus.COMPLETED, orderStatusMapper.toDomain(POrderStatus.COMPLETED));
        Assert.assertEquals(OrderStatus.DROP_OFF_FAIL, orderStatusMapper.toDomain(POrderStatus.DROP_OFF_FAIL));
    }

    @Test
    public void shouldSuccessToProtobufWhenValidData() {
        Assert.assertEquals(POrderStatus.UNRECOGNIZED, orderStatusMapper.toProtobuf(null));
        Assert.assertEquals(POrderStatus.UNKNOWN, orderStatusMapper.toProtobuf(OrderStatus.UNKNOWN));
        Assert.assertEquals(POrderStatus.INIT, orderStatusMapper.toProtobuf(OrderStatus.INIT));
        Assert.assertEquals(POrderStatus.PROCESSING, orderStatusMapper.toProtobuf(OrderStatus.PROCESSING));
        Assert.assertEquals(POrderStatus.DELIVERING, orderStatusMapper.toProtobuf(OrderStatus.DELIVERING));
        Assert.assertEquals(POrderStatus.COMPLETED, orderStatusMapper.toProtobuf(OrderStatus.COMPLETED));
        Assert.assertEquals(POrderStatus.DROP_OFF_FAIL, orderStatusMapper.toProtobuf(OrderStatus.DROP_OFF_FAIL));
    }
}