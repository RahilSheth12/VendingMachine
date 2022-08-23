package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.service.InventoryService;

@RestController
@RequestMapping(path = "/api/v${ApiVersion}/inventory")
public class InventoryController {

    @Autowired
    @Qualifier("inventoryService")
    private InventoryService inventoryService;

    @PatchMapping("")
    private void saveInventory(@RequestParam("product_id") int product_id, @RequestParam("location_id") int location_id,
            @RequestBody Inventory inventory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_ROLE")))
            inventoryService.saveOrUpdate(product_id, location_id, inventory);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Retrieve all inventories across vending machines by product id.
     * 
     * @param product_id product id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    @GetMapping("/product")
    private List<ProductJoin> getInventoryByProduct(@RequestParam("product_id") int product_id) {
        return inventoryService.getInventoriesByProductID(product_id);
    }

}
