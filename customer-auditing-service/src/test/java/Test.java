import au.com.nab.icommerce.customer.auditing.api.CustomerAuditingServiceGrpc;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivity;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesRequest;
import au.com.nab.icommerce.customer.auditing.protobuf.PCustomerActivitiesResponse;
import com.google.protobuf.Int32Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Test {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8401)
                .usePlaintext()
                .build();

        CustomerAuditingServiceGrpc.CustomerAuditingServiceBlockingStub blockingStub = CustomerAuditingServiceGrpc.newBlockingStub(channel);

        PCustomerActivity pCustomerActivity1 = PCustomerActivity.newBuilder()
                .setCustomerId(1)
                .setAction("SEARCH_PRODUCT")
                .setDateTime(System.currentTimeMillis())
                .setBody("{\"merchant_code\":\"highlandscoffee\",\"data\":{\"order_no\":\"210416000400843\",\"hl_order_stt\":\"CANCELLED\",\"ts\":1618787759611,\"sig\":\"3b3d086675aaae03fbf064a0c44dcef3d7b963d1d28f4d3c2f51f5cabcb02dbf\"}}")
                .build();
        Int32Value res1 = blockingStub.addCustomerActivity(pCustomerActivity1);
        System.out.println("addCustomerActivity: " + res1);

        System.out.println("========================================================================");

        PCustomerActivity pCustomerActivity2 = PCustomerActivity.newBuilder()
                .setCustomerId(1)
                .setAction("VIEW_PRODUCT")
                .setDateTime(System.currentTimeMillis())
                .setBody("{\"productId\":100,\"data\":{\"order_no\":\"210416000400843\",\"hl_order_stt\":\"CANCELLED\",\"ts\":1618787759611,\"sig\":\"3b3d086675aaae03fbf064a0c44dcef3d7b963d1d28f4d3c2f51f5cabcb02dbf\"}}")
                .build();
        PCustomerActivitiesRequest customerActivityListRequest = PCustomerActivitiesRequest.newBuilder()
                .addCustomerActivities(pCustomerActivity1)
                .addCustomerActivities(pCustomerActivity2)
                .build();
        Int32Value res2 = blockingStub.addCustomerActivities(customerActivityListRequest);
        System.out.println("addCustomerActivities: " + res2);

        System.out.println("========================================================================");

        PCustomerActivitiesResponse customerActivityListResponse = blockingStub.getCustomerActivitiesByCustomerId(Int32Value.of(1));
        System.out.println("getCustomerActivitiesByCustomerId: " + customerActivityListResponse);

        channel.shutdown();
    }
}
