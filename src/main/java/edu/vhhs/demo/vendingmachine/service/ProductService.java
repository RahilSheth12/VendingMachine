package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import edu.vhhs.demo.vendingmachine.repository.ProductRepository;
import  edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.ProductJoin;
public class ProductService {

    @Autowired
    ProductRepository repository;
    
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<Product>();
        repository.findAll().forEach(product -> products.add(product));
        return products;
    }

    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    public void saveOrUpdate(Product product) {
        repository.save(product);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<ProductJoin> getAvailableProductsByLocation(int location_id) {
        return repository.findAllProductJoin(location_id);
    }
    
}
