package edu.vhhs.demo.vendingmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import edu.vhhs.demo.vendingmachine.service.InventoryService;
import edu.vhhs.demo.vendingmachine.service.ProductService;


@EnableJpaRepositories("edu.vhhs.demo.vendingmachine.repository")
@EntityScan("edu.vhhs.demo.vendingmachine.entity")
@ComponentScan(basePackages = {"edu.vhhs.demo.vendingmachine"})
@SpringBootApplication
public class VendingmachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendingmachineApplication.class, args);
	}

	@Bean
	public ProductService getProductService() {
		return new ProductService();
	}

	@Bean
	public InventoryService getInventoryService() {
		return new InventoryService();
	}

}
