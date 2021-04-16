package au.com.nab.icommerce.customer.service;

import au.com.nab.icommerce.customer.service.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.service.protobuf.PCustomer;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//@SpringBootTest
class CustomerServiceApplicationTests {

    //	@Test
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub = CustomerServiceGrpc.newBlockingStub(channel);

        PCustomer pCustomer = customerServiceBlockingStub.getCustomerById(Int32Value.of(1));
        System.out.println(pCustomer);

//        PCustomer pCustomer = PCustomer.newBuilder()
//                .build();
//        Int32Value response = customerServiceBlockingStub.createCustomer(pCustomer);
//        System.out.println(response);

        channel.shutdown();
    }

}
