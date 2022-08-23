package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * @param product_id  product id
     * @param location_id vending machine id
     * @param inventory   available inventory count for the product
     */
    public void saveOrUpdate(int product_id, int location_id, Inventory inventory) {
        LOGGER.debug("Adding inventory for product({0}) in vending machine({1})", product_id, location_id);
        Optional<Product> productOptional = productRepository.findById(product_id);
        Optional<VendingMachine> vendingMachineOptional = vendingMachineRepository.findById(location_id);
        // Check to see if inventory exist and update existing inventory with new count
        if (productOptional.isPresent() && vendingMachineOptional.isPresent()) {
            Product product = productOptional.get();
            VendingMachine vendingMachine = vendingMachineOptional.get();
            List<Inventory> product_inventories = product.getInventories();
            boolean isNewInventory = true;
            // update existing inventory
            if (product_inventories != null && !product_inventories.isEmpty()) {
                for (Inventory product_inventory : product_inventories) {
                    int currentLocationID = product_inventory.getVendingMachine().getId();
                    if (currentLocationID == location_id) {
                        isNewInventory = false;
                        product_inventory.setCount(inventory.getCount());
                    }
                }
                // add new inventory of the product in newer vending machine
                if (isNewInventory) {
                    product_inventories = new ArrayList<Inventory>();
                    // add new inventory new location
                    inventory.setVendingMachine(vendingMachine);
                    inventory.setProduct(product);
                    product_inventories.add(inventory);
                    product.setInventories(product_inventories);
                    vendingMachine.setInventories(product_inventories);
                }
            } else {
                // add new product inventory in newer vending machine
                product_inventories = new ArrayList<Inventory>();
                inventory.setVendingMachine(vendingMachine);
                inventory.setProduct(product);
                product_inventories.add(inventory);
                product.setInventories(product_inventories);
                vendingMachine.setInventories(product_inventories);
            }
            productRepository.save(product);
        }
    }

    /**
     * Retrieve all inventories by product id across vending machines
     * 
     * @param product_id product id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    public List<ProductJoin> getInventoriesByProductID(int product_id) {
        LOGGER.debug("Retrieve all inventories for product({0}) across all vending machine.", product_id);
        return inventoryRepository.findAllInventoryByProductIdNative(product_id);
    }

}
