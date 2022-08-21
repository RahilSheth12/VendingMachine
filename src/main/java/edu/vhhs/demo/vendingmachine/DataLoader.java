package edu.vhhs.demo.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Autowired
    public DataLoader(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Product product = new Product("Orange", "Red Orange", "images/orange.jpg", (float) 1.0);
        Inventory inventory = new Inventory(1, 1);
        product.setInventory(inventory);
        inventory.setProduct(product);
        this.productRepository.save(product);
    }

}
