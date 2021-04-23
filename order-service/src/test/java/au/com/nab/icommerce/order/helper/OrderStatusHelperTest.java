package au.com.nab.icommerce.order.helper;

import au.com.nab.icommerce.order.domain.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderStatusHelperTest {

    @Test
    public void shouldSuccessIsChangeStatusValidWhenValidOrderStatusFlow() {
        Assert.assertTrue(OrderStatusHelper.isChangeStatusValid(OrderStatus.INIT, OrderStatus.PROCESSING));
        Assert.assertTrue(OrderStatusHelper.isChangeStatusValid(OrderStatus.PROCESSING, OrderStatus.DELIVERING));
        Assert.assertTrue(OrderStatusHelper.isChangeStatusValid(OrderStatus.DELIVERING, OrderStatus.COMPLETED));
        Assert.assertTrue(OrderStatusHelper.isChangeStatusValid(OrderStatus.DELIVERING, OrderStatus.DROP_OFF_FAIL));
    }

    @Test
    public void shouldFailIsChangeStatusValidWhenInvalidOrderStatusFlow() {
        Assert.assertFalse(OrderStatusHelper.isChangeStatusValid(OrderStatus.PROCESSING, OrderStatus.INIT));
        Assert.assertFalse(OrderStatusHelper.isChangeStatusValid(OrderStatus.DELIVERING, OrderStatus.PROCESSING));
        Assert.assertFalse(OrderStatusHelper.isChangeStatusValid(OrderStatus.COMPLETED, OrderStatus.DELIVERING));
        Assert.assertFalse(OrderStatusHelper.isChangeStatusValid(OrderStatus.DROP_OFF_FAIL, OrderStatus.DELIVERING));
    }

}