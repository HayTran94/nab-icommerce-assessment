package au.com.nab.icommerce.api.gateway.controller;

import au.com.nab.icommerce.api.gateway.client.CartServiceClient;
import au.com.nab.icommerce.api.gateway.client.CustomerServiceClient;
import au.com.nab.icommerce.api.gateway.client.OrderServiceClient;
import au.com.nab.icommerce.api.gateway.client.ProductServiceClient;
import au.com.nab.icommerce.api.gateway.common.ApiMessage;
import au.com.nab.icommerce.api.gateway.dto.request.UpdateOrderStatusRequest;
import au.com.nab.icommerce.api.gateway.mapper.response.OrderResponseMapper;
import au.com.nab.icommerce.api.gateway.security.SecurityHelper;
import au.com.nab.icommerce.cart.protobuf.PCart;
import au.com.nab.icommerce.cart.protobuf.PCartItem;
import au.com.nab.icommerce.common.error.ErrorCode;
import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrderItem;
import au.com.nab.icommerce.order.protobuf.POrderStatus;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import au.com.nab.icommerce.product.protobuf.PProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private OrderResponseMapper orderResponseMapper;

    @PostMapping("/customer/{customerId}")
    public ApiMessage placeOrder(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            // Get customer cart
            PCart cart = cartServiceClient.getCustomerCart(customerId);
            if (cart == null) {
                return ApiMessage.CART_EMPTY;
            }

            // Get products in cart
            List<PCartItem> items = cart.getItemsList();
            List<Integer> productIds = items.stream()
                    .map(PCartItem::getProductId)
                    .collect(Collectors.toList());
            Map<Integer, PProduct> productMap = productServiceClient.mGetProductsByIds(productIds);

            // Prepare order data
            int totalAmount = 0;
            List<POrderItem> orderItems = new ArrayList<>();
            for (PCartItem cartItem : items) {
                int productId = cartItem.getProductId();
                PProduct product = productMap.get(productId);

                totalAmount += cartItem.getQuantity() * product.getPrice();
                POrderItem orderItem = POrderItem.newBuilder()
                        .setProductId(productId)
                        .setPrice(product.getPrice())
                        .setQuantity(cartItem.getQuantity())
                        .build();
                orderItems.add(orderItem);
            }

            // Process place order
            POrder order = POrder.newBuilder()
                    .setCustomerId(customerId)
                    .setCustomerName(customer.getName())
                    .setTotalAmount(totalAmount)
                    .setStatus(POrderStatus.INIT)
                    .addAllItems(orderItems)
                    .build();
            int response = orderServiceClient.createOrder(order);
            if (ErrorCodeHelper.isFail(response)) {
                return ApiMessage.ORDER_CREATE_FAILED;
            }

            // Clear customer cart
            cartServiceClient.clearCustomerCart(customerId);

            return ApiMessage.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{orderId}")
    public ApiMessage getOrderById(@PathVariable Integer orderId) {
        try {
            POrder order = orderServiceClient.getOrderById(orderId);
            if (order == null) {
                return ApiMessage.ORDER_NOT_FOUND;
            }

            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != order.getCustomerId()) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            return ApiMessage.success(orderResponseMapper.toDomain(order));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/customer/{customerId}")
    public ApiMessage getOrdersByCustomerId(@PathVariable Integer customerId) {
        try {
            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != customerId) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            List<POrder> orders = orderServiceClient.getOrdersByCustomerId(customerId);
            return ApiMessage.success(orderResponseMapper.toDomainList(orders));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping("/status")
    public ApiMessage updateOrderStatus(@RequestBody @Valid UpdateOrderStatusRequest updateOrderStatusRequest) {
        try {
            Integer orderId = updateOrderStatusRequest.getOrderId();
            POrderStatus newStatus = updateOrderStatusRequest.getStatus();

            POrder order = orderServiceClient.getOrderById(orderId);
            if (order == null) {
                return ApiMessage.ORDER_NOT_FOUND;
            }

            PCustomer customer = SecurityHelper.getCustomer();
            if (customer.getId() != order.getCustomerId()) {
                return ApiMessage.CUSTOMER_VIOLATION;
            }

            PUpdateOrderStatusRequest pUpdateOrderStatusRequest = PUpdateOrderStatusRequest.newBuilder()
                    .setOrderId(orderId)
                    .setStatus(newStatus)
                    .build();
            int response = orderServiceClient.updateOrderStatus(pUpdateOrderStatusRequest);
            if (ErrorCodeHelper.isSuccess(response)) {
                return ApiMessage.SUCCESS;
            }

            if (response == ErrorCode.ORDER_INVALID_STATUS) {
                POrderStatus curStatus = order.getStatus();
                return ApiMessage.ORDER_INVALID_STATUS_FLOW.setMessageArgs(curStatus, newStatus);
            }

            return ApiMessage.FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

}
