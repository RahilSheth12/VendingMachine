package edu.vhhs.demo.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.UserDetails;
import edu.vhhs.demo.vendingmachine.entity.UserRole;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.repository.UserDetailsRepository;
import edu.vhhs.demo.vendingmachine.service.InventoryService;
import edu.vhhs.demo.vendingmachine.service.ProductService;
import edu.vhhs.demo.vendingmachine.service.VendingMachineService;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    @Qualifier("vendingMachineService")
    private VendingMachineService vendingMachineService;

    @Autowired
    @Qualifier("inventoryService")
    private InventoryService inventoryService;

    @Autowired
    @Qualifier("userDetailsRepository")
    private UserDetailsRepository userDetailsRepository;

    @Override
    public void run(String... args) throws Exception {
        Product product = new Product("Orange", "Red Orange", "images/orange.jpg", (float) 1.0);
        Inventory inventory = new Inventory(5);
        VendingMachine vendingMachine = new VendingMachine("Room 301");

        product = productService.saveOrUpdate(product);
        vendingMachine = vendingMachineService.saveOrUpdate(vendingMachine);
        inventoryService.saveOrUpdate(product.getId(), vendingMachine.getId(), inventory);
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("jigsheth@gmail.com");
        userDetails.setName("Jignesh Sheth");
        userDetails.setRole(UserRole.ADMIN_ROLE);
        userDetailsRepository.save(userDetails);
    }

}
