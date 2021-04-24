package au.com.nab.icommerce.order.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.order.PrepareData;
import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.protobuf.*;
import au.com.nab.icommerce.order.repository.OrderRepository;
import com.google.protobuf.Int32Value;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final int ORDER_ID = 333;

    // ============================ CREATE ORDER ============================

    @Test
    public void shouldSuccessCreateOrderWhenValidData() {
        // GIVEN
        POrder pOrder = PrepareData.prepareTransientPOrder();

        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(invocation -> {
            Order argument = (Order) invocation.getArguments()[0];
            argument.setId(ORDER_ID);    // set id when save
            return argument;
        });

        // WHEN
        Int32Value response = orderService.createOrder(pOrder);

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(ORDER_ID, response.getValue());
    }

    @Test
    public void shouldFailCreateOrderWhenNullData() {
        // GIVEN
        POrder pOrder = null;

        // WHEN
        Int32Value response = orderService.createOrder(pOrder);

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(ErrorCode.INVALID_DATA, response.getValue());
    }

    // ============================ UPDATE ORDER STATUS ============================

    @Test
    public void shouldSuccessUpdateOrderStatusWhenValidData() {
        // GIVEN
        Order order = PrepareData.preparePersistentOrder();
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        // WHEN
        PUpdateOrderStatusRequest updateOrderStatusRequest = PUpdateOrderStatusRequest.newBuilder()
                .setOrderId(ORDER_ID)
                .setStatus(POrderStatus.PROCESSING)
                .build();
        Int32Value response = orderService.updateOrderStatus(updateOrderStatusRequest);

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(ErrorCode.SUCCESS, response.getValue());
    }

    @Test
    public void shouldFailUpdateOrderStatusWhenOrderNotFound() {
        // GIVEN
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.ofNullable(null));

        // WHEN
        PUpdateOrderStatusRequest updateOrderStatusRequest = PUpdateOrderStatusRequest.newBuilder()
                .setOrderId(ORDER_ID)
                .setStatus(POrderStatus.PROCESSING)
                .build();
        Int32Value response = orderService.updateOrderStatus(updateOrderStatusRequest);

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(ErrorCode.FAILED, response.getValue());
    }

    @Test
    public void shouldFailUpdateOrderStatusWhenInvalidOrderStatusFlow() {
        // GIVEN
        Order order = PrepareData.preparePersistentOrder();
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        // WHEN
        PUpdateOrderStatusRequest updateOrderStatusRequest = PUpdateOrderStatusRequest.newBuilder()
                .setOrderId(ORDER_ID)
                .setStatus(POrderStatus.COMPLETED)
                .build();
        Int32Value response = orderService.updateOrderStatus(updateOrderStatusRequest);

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(ErrorCode.ORDER_STATUS_INVALID_FLOW, response.getValue());
    }

    // ============================ GET ORDER BY ID ============================

    @Test
    public void shouldSuccessGetOrderByIdWhenValidData() {
        // GIVEN
        Order order = PrepareData.preparePersistentOrder();
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        // WHEN
        POrderResponse response = orderService.getOrderById(Int32Value.of(ORDER_ID));

        // THEN
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(ErrorCode.SUCCESS, response.getCode());
    }

    @Test
    public void shouldSuccessGetOrderByIdWhenOrderNotFound() {
        // GIVEN
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // WHEN
        POrderResponse response = orderService.getOrderById(Int32Value.of(ORDER_ID));

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(POrder.getDefaultInstance(), response.getData());
        Assert.assertEquals(ErrorCode.FAILED, response.getCode());
    }

    // ============================ GET ORDERS BY CUSTOMER ID ============================

    @Test
    public void shouldSuccessGetOrdersByCustomerIdWhenValidData() {
        // GIVEN
        List<Order> orders = PrepareData.preparePersistentOrders();
        Mockito.when(orderRepository.findAllByCustomerId(ORDER_ID)).thenReturn(orders);

        // WHEN
        POrdersResponse response = orderService.getOrdersByCustomerId(Int32Value.of(ORDER_ID));

        // THEN
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getDataList());
        Assert.assertEquals(ErrorCode.SUCCESS, response.getCode());
        Assert.assertEquals(2, response.getDataCount());
        Assert.assertEquals(333, response.getDataList().get(0).getId());
        Assert.assertEquals(444, response.getDataList().get(1).getId());
    }

    @Test
    public void shouldSuccessGetOrdersByCustomerIdWhenOrdersEmpty() {
        // GIVEN
        Mockito.when(orderRepository.findAllByCustomerId(ORDER_ID)).thenReturn(Collections.emptyList());

        // WHEN
        POrdersResponse response = orderService.getOrdersByCustomerId(Int32Value.of(ORDER_ID));

        // THEN
        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getDataCount());
        Assert.assertEquals(ErrorCode.SUCCESS, response.getCode());
    }

}