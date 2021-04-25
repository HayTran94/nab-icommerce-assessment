package au.com.nab.icommerce.order.service.impl;

import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.order.domain.Order;
import au.com.nab.icommerce.order.domain.OrderStatus;
import au.com.nab.icommerce.order.helper.OrderStatusHelper;
import au.com.nab.icommerce.order.mapper.OrderMapper;
import au.com.nab.icommerce.order.mapper.OrderStatusMapper;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrderResponse;
import au.com.nab.icommerce.order.protobuf.POrdersResponse;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import au.com.nab.icommerce.order.repository.OrderRepository;
import au.com.nab.icommerce.order.service.OrderService;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final OrderStatusMapper orderStatusMapper = OrderStatusMapper.INSTANCE;

    @Override
    public Int32Value createOrder(POrder pOrder) {
        int response = ErrorCode.FAILED;
        try {
            if (pOrder == null) {
                return Int32Value.of(ErrorCode.INVALID_DATA);
            }

            Order order = orderMapper.toDomain(pOrder);
            order = orderRepository.save(order);
            if (order.getId() > 0) {
                response = order.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }

    @Override
    public Int32Value updateOrderStatus(PUpdateOrderStatusRequest pUpdateOrderStatusRequest) {
        int response = ErrorCode.FAILED;
        try {
            int orderId = pUpdateOrderStatusRequest.getOrderId();
            OrderStatus newStatus = orderStatusMapper.toDomain(pUpdateOrderStatusRequest.getStatus());

            // Check order is existed
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                return Int32Value.of(response);
            }

            // Validate change status flow
            Order order = orderOptional.get();
            OrderStatus curStatus = order.getStatus();
            boolean changeStatusValid = OrderStatusHelper.isChangeStatusValid(curStatus, newStatus);
            if (!changeStatusValid) {
                return Int32Value.of(ErrorCode.ORDER_STATUS_INVALID_FLOW);
            }

            // Update status
            order.setStatus(newStatus);
            orderRepository.save(order);
            response = ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int32Value.of(response);
    }

    @Override
    public POrderResponse getOrderById(Int32Value id) {
        POrderResponse.Builder response = POrderResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            Optional<Order> order = orderRepository.findById(id.getValue());
            if (order.isPresent()) {
                POrder pOrder = orderMapper.toProtobuf(order.get());
                return response.setCode(ErrorCode.SUCCESS).setData(pOrder).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

    @Override
    public POrdersResponse getOrdersByCustomerId(Int32Value customerId) {
        POrdersResponse.Builder response = POrdersResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            List<Order> orders = orderRepository.findAllByCustomerId(customerId.getValue());
            List<POrder> pOrders = orderMapper.toProtobufList(orders);
            return response.setCode(ErrorCode.SUCCESS).addAllData(pOrders).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.build();
    }

}
