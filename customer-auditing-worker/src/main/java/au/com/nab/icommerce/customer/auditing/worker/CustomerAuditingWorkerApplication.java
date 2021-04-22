package au.com.nab.icommerce.customer.auditing.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("au.com.nab.icommerce")
public class CustomerAuditingWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAuditingWorkerApplication.class, args);
	}

}
