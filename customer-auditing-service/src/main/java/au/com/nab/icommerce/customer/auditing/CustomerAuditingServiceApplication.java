package au.com.nab.icommerce.customer.auditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerAuditingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAuditingServiceApplication.class, args);
	}

}
