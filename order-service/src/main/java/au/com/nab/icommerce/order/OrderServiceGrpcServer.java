package au.com.nab.icommerce.order;

import au.com.nab.icommerce.order.api.OrderServiceGrpc;
import au.com.nab.icommerce.order.protobuf.POrder;
import au.com.nab.icommerce.order.protobuf.POrdersResponse;
import au.com.nab.icommerce.order.protobuf.PUpdateOrderStatusRequest;
import au.com.nab.icommerce.order.service.OrderService;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class OrderServiceGrpcServer extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderService orderService;

    @Override
    public void createOrder(POrder request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = orderService.createOrder(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void updateOrderStatus(PUpdateOrderStatusRequest request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = orderService.updateOrderStatus(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrderById(Int32Value request, StreamObserver<POrder> responseObserver) {
        POrder result = orderService.getOrderById(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrdersByCustomerId(Int32Value request, StreamObserver<POrdersResponse> responseObserver) {
        POrdersResponse result = orderService.getOrdersByCustomerId(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
