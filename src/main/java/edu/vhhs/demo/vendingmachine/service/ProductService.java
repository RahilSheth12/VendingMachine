package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.ProductRepository;

public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<Product>();
        productRepository.findAll().forEach(product -> products.add(product));
        return products;
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public List<ProductJoin> getAvailableProductsByLocation(int location_id) {
        return productRepository.findAllProductJoin(location_id);
    }

}
