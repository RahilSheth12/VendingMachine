package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRepository productRepository;
    
    public List<Inventory> getAllInventory() {
        List<Inventory> inventories = new ArrayList<Inventory>();
        inventoryRepository.findAll().forEach(inventory -> inventories.add(inventory));
        return inventories;
    }

    public Optional<Inventory> getInventoryById(int id) {
        return inventoryRepository.findById(id);
    }

    public void saveOrUpdate(int product_id, Inventory inventory) {
        Optional<Product> productOptional = productRepository.findById(product_id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Inventory product_inventory = product.getInventory();
            if (product_inventory != null) {
                product_inventory.setCount(inventory.getCount());
                product_inventory.setLocation(inventory.getLocation());
                product.setInventory(product_inventory);
                product_inventory.setProduct(product);
            } else {
                product.setInventory(inventory);
                inventory.setProduct(product);
            }
            productRepository.save(product);
        }
    }

    public void delete(int product_id) {
        inventoryRepository.deleteByProductId(product_id);
    }

    public Inventory getInventoryByProductID(int product_id) {
        return inventoryRepository.findByProductId(product_id);
    }

    public List<Inventory> getInventoryByLocation(int location_id) {
        return inventoryRepository.findByLocation(location_id);
    }
    
}
