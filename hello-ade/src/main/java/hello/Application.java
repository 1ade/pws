package hello;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient

public class Application {
	private Log log = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    CommandLineRunner consume(final DiscoveryClient discoveryClient) {
    	return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				// print out service instances
				System.out.println("=====================================");
				for (String svc : discoveryClient.getServices()) {
					System.out.println("service = " + svc);
					List<ServiceInstance> instances = discoveryClient.getInstances(svc);
					for (ServiceInstance si : instances) {
						System.out.println("\t" + si);
					}
				}
				System.out.println("=====================================");
				System.out.println("local");
				System.out.println("\t" + discoveryClient.getLocalServiceInstance());

			}
		};
    	
    }
    

    	

}
