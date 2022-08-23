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
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;

@Service("productService")
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("inventoryRepository")
    private InventoryRepository inventoryRepository;

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<Product>();
        productRepository.findAll().forEach(product -> products.add(product));
        return products;
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    /**
     * Delete all inventories for the product across all vending machine before
     * deleting product from catalog
     * 
     * @param id product id
     */
    public void delete(int id) {
        LOGGER.debug("Deleting product({0}) across all vending machine.", id);
        Product product = getProductById(id).isPresent() ? getProductById(id).get() : new Product();
        List<Inventory> inventories = inventoryRepository.findByProduct(product);
        ArrayList<Inventory> updatedInventories = new ArrayList<Inventory>();
        for (Inventory inventory : inventories) {
            inventory.setProduct(null);
            inventory.setVendingMachine(null);
            updatedInventories.add(inventory);
        }
        inventoryRepository.deleteAll(updatedInventories);
        productRepository.delete(product);
    }

    /**
     * Retrieve all products by vending machine id
     * 
     * @param location_id vending machine id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    public List<ProductJoin> getAvailableProductsByLocation(int location_id) {
        return productRepository.findAllProductJoin(location_id);
    }

}
