package au.com.nab.icommerce.product.command;

import au.com.nab.icommerce.product.api.ProductCommandServiceGrpc;
import au.com.nab.icommerce.product.command.service.ProductService;
import au.com.nab.icommerce.product.protobuf.PProduct;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ProductCommandServiceGrpcServer extends ProductCommandServiceGrpc.ProductCommandServiceImplBase {

    @Autowired
    private ProductService productService;

    @Override
    public void createProduct(PProduct request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = productService.createProduct(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(PProduct request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = productService.updateProduct(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

}
