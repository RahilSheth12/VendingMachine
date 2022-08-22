package edu.vhhs.demo.vendingmachine;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final VendingMachineRepository vendingMachineRepository;

    @Autowired
    public DataLoader(ProductRepository productRepository, InventoryRepository inventoryRepository,
            VendingMachineRepository vendingMachineRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.vendingMachineRepository = vendingMachineRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Product product = new Product("Orange", "Red Orange", "images/orange.jpg", (float) 1.0);
        Inventory inventory = new Inventory(1);
        VendingMachine vendingMachine = new VendingMachine("Room 301");
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        inventories.add(inventory);
        this.vendingMachineRepository.save(vendingMachine);
        this.productRepository.save(product);
    }

}
