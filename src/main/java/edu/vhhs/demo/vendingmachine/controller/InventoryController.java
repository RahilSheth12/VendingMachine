package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.service.InventoryService;

@RestController
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    // @GetMapping("/api/inventory")
    // private List<Inventory> getAllInventory() {
    //     return inventoryService.getAllInventory();
    // }    

    // @GetMapping("/api/inventory/{id}")
    // private Optional<Inventory> getInventory(@PathVariable("id") int id) {
    //     return inventoryService.getInventoryById(id);
    // }
    
    @PatchMapping("/api/inventory")  
    private void saveInventory(@RequestParam("product_id") int product_id, @RequestBody Inventory inventory)   
    {  
        inventoryService.saveOrUpdate(product_id,inventory);  
    }

    @DeleteMapping("/api/inventory/{product_id}")
    private void deleteInventoryByProduct(@PathVariable("product_id") int product_id) {
        inventoryService.delete(product_id);
    }

    @GetMapping("/api/inventory/product")
    private Inventory getInventoryByProduct(@RequestParam("product_id") int product_id) {
        return inventoryService.getInventoryByProductID(product_id);
    }

    @GetMapping("/api/inventory/location")
    private List<Inventory> getInventoryByLocation(@RequestParam("location_id") int location_id) {
        return inventoryService.getInventoryByLocation(location_id);
    }

}
