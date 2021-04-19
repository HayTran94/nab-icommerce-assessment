package au.com.nab.icommerce.product.query;

import au.com.nab.icommerce.product.api.ProductQueryServiceGrpc;
import au.com.nab.icommerce.product.protobuf.PProduct;
import au.com.nab.icommerce.product.protobuf.PProductCriteria;
import au.com.nab.icommerce.product.protobuf.PProductsResponse;
import au.com.nab.icommerce.product.query.service.ProductService;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ProductQueryServiceGrpcServer extends ProductQueryServiceGrpc.ProductQueryServiceImplBase {

    @Autowired
    private ProductService customerService;

    @Override
    public void getProductsById(Int32Value request, StreamObserver<PProduct> responseObserver) {
        PProduct result = customerService.getProductById(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByCriteria(PProductCriteria request, StreamObserver<PProductsResponse> responseObserver) {
        PProductsResponse result = customerService.getProductsByCriteria(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

}
