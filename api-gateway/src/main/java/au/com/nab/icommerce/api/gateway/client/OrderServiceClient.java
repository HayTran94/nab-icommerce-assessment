package au.com.nab.icommerce.api.gateway.client;

import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import au.com.nab.icommerce.order.api.OrderServiceGrpc;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrderResponse;
import au.com.nab.icommerce.order.protobuf.POrdersResponse;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import com.google.protobuf.Int32Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderServiceClient {

    @Autowired
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    public int createOrder(POrder pOrder) {
        Int32Value response = orderServiceBlockingStub.createOrder(pOrder);
        return response.getValue();
    }

    public int updateOrderStatus(PUpdateOrderStatusRequest pUpdateOrderStatusRequest) {
        Int32Value response = orderServiceBlockingStub.updateOrderStatus(pUpdateOrderStatusRequest);
        return response.getValue();
    }

    public POrder getOrderById(Integer orderId) {
        POrderResponse response = orderServiceBlockingStub.getOrderById(Int32Value.of(orderId));
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }

    public List<POrder> getOrdersByCustomerId(Integer customerId) {
        POrdersResponse response = orderServiceBlockingStub.getOrdersByCustomerId(Int32Value.of(customerId));
        if (ErrorCodeHelper.isSuccess(response.getCode())) {
            return response.getDataList();
        }
        return Collections.emptyList();
    }
}
