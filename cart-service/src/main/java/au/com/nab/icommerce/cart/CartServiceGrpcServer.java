package au.com.nab.icommerce.cart;

import au.com.nab.icommerce.cart.api.CartServiceGrpc;
import au.com.nab.icommerce.cart.protobuf.PAddCartItemsRequest;
import au.com.nab.icommerce.cart.protobuf.PCartResponse;
import au.com.nab.icommerce.cart.protobuf.PRemoveCartItemsRequest;
import au.com.nab.icommerce.cart.service.CartService;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CartServiceGrpcServer extends CartServiceGrpc.CartServiceImplBase {

    @Autowired
    private CartService cartService;

    @Override
    public void addCartItems(PAddCartItemsRequest request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = cartService.addCartItems(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void removeCartItems(PRemoveCartItemsRequest request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = cartService.removeCartItems(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerCart(Int32Value request, StreamObserver<PCartResponse> responseObserver) {
        PCartResponse result = cartService.getCustomerCart(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void clearCustomerCart(Int32Value request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = cartService.clearCustomerCart(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
