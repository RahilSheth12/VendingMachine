package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    VendingMachineRepository vendingMachineRepository;

    public List<Inventory> getAllInventory() {
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
        return inventoryRepository.findAllInventoryByProductIdNative(product_id);
    }

}
