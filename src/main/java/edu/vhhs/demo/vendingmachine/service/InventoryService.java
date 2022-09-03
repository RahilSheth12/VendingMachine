package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

@Service("inventoryService")
public class InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("productRepository")
    ProductRepository productRepository;

    @Autowired
    @Qualifier("vendingMachineRepository")
    VendingMachineRepository vendingMachineRepository;

    public List<Inventory> getAllInventory() {
        LOGGER.debug("Retrieve all inventory from inventories.");
        List<Inventory> inventories = new ArrayList<Inventory>();
        inventoryRepository.findAll().forEach(inventory -> inventories.add(inventory));
        return inventories;
    }

    public Optional<Inventory> getInventoryById(int id) {
        return inventoryRepository.findById(id);
    }

    /**
     * Add product inventory to the vending machine
     * 
     * @param productID  product id
     * @param locationID vending machine id
     * @param inventory  available inventory count for the product
     */
    public void saveOrUpdate(int productID, int locationID, Inventory inventory) {
        LOGGER.debug("Adding inventory for product({0}) in vending machine({1})", productID, locationID);
        Optional<Product> productOptional = productRepository.findById(productID);
        Optional<VendingMachine> vendingMachineOptional = vendingMachineRepository.findById(locationID);
        // Check to see if inventory exist and update existing inventory with new count
        if (productOptional.isPresent() && vendingMachineOptional.isPresent()) {
            Product product = productOptional.get();
            VendingMachine vendingMachine = vendingMachineOptional.get();
            Set<Inventory> productInventories = product.getInventories();
            boolean isNewInventory = true;
            // update existing inventory
            if (productInventories != null && !productInventories.isEmpty()) {
                for (Inventory productInventory : productInventories) {
                    int currentLocationID = productInventory.getVendingMachine().getId();
                    if (currentLocationID == locationID) {
                        isNewInventory = false;
                        productInventory.setCount(inventory.getCount());
                        productInventories = new HashSet<Inventory>();
                        productInventories.add(productInventory);
                        product.setInventories(productInventories);
                        vendingMachine.setInventories(productInventories);
                    }
                }
                // add new inventory of the product in newer vending machine
                if (isNewInventory) {
                    productInventories = new HashSet<Inventory>();
                    // add new inventory new location
                    inventory.setVendingMachine(vendingMachine);
                    inventory.setProduct(product);
                    productInventories.add(inventory);
                    product.setInventories(productInventories);
                    vendingMachine.setInventories(productInventories);
                }
            } else {
                // add new product inventory in newer vending machine
                productInventories = new HashSet<Inventory>();
                inventory.setVendingMachine(vendingMachine);
                inventory.setProduct(product);
                productInventories.add(inventory);
                product.setInventories(productInventories);
                vendingMachine.setInventories(productInventories);
            }
            product = productRepository.save(product);
            for (Inventory _inventory : product.getInventories()) {
                LOGGER.debug("products_inventories: " + _inventory);
            }
            vendingMachine = vendingMachineRepository.save(vendingMachine);
            for (Inventory _inventory : vendingMachine.getInventories()) {
                LOGGER.debug("vending_machines_inventories: " + _inventory);
            }
        }
    }

    /**
     * Retrieve all inventories by product id across vending machines
     * 
     * @param productID product id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    public List<ProductJoin> getInventoriesByProductID(int productID) {
        LOGGER.debug("Retrieve all inventories for product({0}) across all vending machine.", productID);
        return inventoryRepository.findAllInventoryByProductIdNative(productID);
    }

}
