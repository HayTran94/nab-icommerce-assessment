package au.com.nab.icommerce.customer;

import au.com.nab.icommerce.customer.api.CustomerServiceGrpc;
import au.com.nab.icommerce.customer.protobuf.PCustomerResponse;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//@SpringBootTest
class CustomerServiceApplicationTests {

    //	@Test
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8001)
                .usePlaintext()
                .build();

        CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub = CustomerServiceGrpc.newBlockingStub(channel);

        PCustomerResponse response = customerServiceBlockingStub.getCustomerById(Int32Value.of(1));
        System.out.println(response);

        channel.shutdown();
    }

}
