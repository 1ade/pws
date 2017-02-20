package com.example;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@Controller
@EnableEurekaClient
public class DemoCallHelloAdeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCallHelloAdeApplication.class, args);
	}
	//fetching value from config server via service registry
	@Value("${hello.service}")
	private String serviceName;


	 @Bean
	    CommandLineRunner consume(final DiscoveryClient discoveryClient) {
	    	return new CommandLineRunner() {
				@Override
				public void run(String... args) throws Exception {

					// print out sevice instances
					System.out.println("=====================================");
					System.out.println("serviceName: "+serviceName);
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

    
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private DiscoveryClient dc;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello(@RequestParam(value = "salutation", defaultValue = "Hello") String salutation,
			@RequestParam(value = "name", defaultValue = "Bob") String name) throws JsonParseException, JsonMappingException, IOException {		

		ResponseEntity<String> resp = rest.getForEntity("//"+serviceName+"/greeting?salutation="+salutation+"&name="+name, String.class);
		ObjectMapper om = new ObjectMapper();
		Greeting greeting = om.readValue(resp.getBody(), Greeting.class);		
		return greeting.getMessage();
	}

	
	
	
}
