package au.com.nab.icommerce.product.auditing.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("au.com.nab.icommerce")
public class ProductAuditingWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAuditingWorkerApplication.class, args);
	}

}
