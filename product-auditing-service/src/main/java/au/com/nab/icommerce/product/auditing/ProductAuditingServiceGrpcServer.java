package au.com.nab.icommerce.product.auditing;

import au.com.nab.icommerce.product.auditing.api.ProductAuditingServiceGrpc;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistoriesResponse;
import au.com.nab.icommerce.product.auditing.protobuf.PProductPriceHistory;
import au.com.nab.icommerce.product.auditing.service.ProductPriceHistoryService;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ProductAuditingServiceGrpcServer extends ProductAuditingServiceGrpc.ProductAuditingServiceImplBase {

    @Autowired
    private ProductPriceHistoryService customerService;

    @Override
    public void addProductPriceHistory(PProductPriceHistory request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = customerService.addProductPriceHistory(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductPriceHistories(Int32Value request, StreamObserver<PProductPriceHistoriesResponse> responseObserver) {
        PProductPriceHistoriesResponse result = customerService.getProductPriceHistories(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

}
