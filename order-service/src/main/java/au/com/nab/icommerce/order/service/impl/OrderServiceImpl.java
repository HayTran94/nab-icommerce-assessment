package au.com.nab.icommerce.order.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.domain.OrderStatus;
import au.com.nab.icommerce.order.helper.OrderStatusHelper;
import au.com.nab.icommerce.order.mapper.OrderMapper;
import au.com.nab.icommerce.order.mapper.OrderStatusMapper;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrdersResponse;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import au.com.nab.icommerce.order.repository.OrderRepository;
import au.com.nab.icommerce.order.service.OrderService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Int32Value createOrder(POrder pOrder) {
        int res = ErrorCode.FAILED;
        try {
            Order order = orderMapper.toDomain(pOrder);
            order = orderRepository.save(order);
            if (order.getId() > 0) {
                res = order.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public Int32Value updateOrderStatus(PUpdateOrderStatusRequest pUpdateOrderStatusRequest) {
        int res = ErrorCode.FAILED;
        try {
            int orderId = pUpdateOrderStatusRequest.getOrderId();
            OrderStatus newStatus = orderStatusMapper.toDomain(pUpdateOrderStatusRequest.getStatus());

            // Check order is existed
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                return Int32Value.of(res);
            }

            // Validate change status flow
            Order order = orderOptional.get();
            OrderStatus curStatus = order.getStatus();
            boolean changeStatusValid = OrderStatusHelper.isChangeStatusValid(curStatus, newStatus);
            if (!changeStatusValid) {
                return Int32Value.of(res);
            }

            // Update status
            order.setStatus(newStatus);
            orderRepository.save(order);
            res = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(res);
    }

    @Override
    public POrder getOrderById(Int32Value id) {
        POrder pOrder = null;
        try {
            Optional<Order> order = orderRepository.findById(id.getValue());
            if (order.isPresent()) {
                pOrder = orderMapper.toProtobuf(order.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pOrder;
    }

    @Override
    public POrdersResponse getOrdersByCustomerId(Int32Value customerId) {
        List<POrder> pOrders = Collections.emptyList();
        try {
            List<Order> orders = orderRepository.findAllByCustomerId(customerId.getValue());
            pOrders = orderMapper.toProtobufList(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return POrdersResponse.newBuilder().addAllOrders(pOrders).build();
    }

}
