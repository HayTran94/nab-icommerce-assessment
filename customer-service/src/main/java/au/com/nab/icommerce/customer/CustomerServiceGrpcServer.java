package au.com.nab.icommerce.customer;

import au.com.nab.icommerce.customer.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.protobuf.GetCustomerResponse;
import au.com.nab.icommerce.customer.protobuf.PCustomer;
import au.com.nab.icommerce.customer.service.CustomerService;
import com.google.protobuf.Int32Value;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CustomerServiceGrpcServer extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Autowired
    private CustomerService customerService;

    @Override
    public void createCustomer(PCustomer request, StreamObserver<Int32Value> responseObserver) {
        Int32Value result = customerService.createCustomer(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerById(Int32Value request, StreamObserver<GetCustomerResponse> responseObserver) {
        GetCustomerResponse result = customerService.getCustomerById(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
