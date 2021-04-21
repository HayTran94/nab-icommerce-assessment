package au.com.nab.icommerce.order.service;

import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrderResponse;
import au.com.nab.icommerce.order.protobuf.POrdersResponse;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import com.google.protobuf.Int32Value;

public interface OrderService {
    Int32Value createOrder(POrder pOrder);

    Int32Value updateOrderStatus(PUpdateOrderStatusRequest pUpdateOrderStatusRequest);

    POrderResponse getOrderById(Int32Value id);

    POrdersResponse getOrdersByCustomerId(Int32Value customerId);
}
