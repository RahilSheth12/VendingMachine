package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/api/product")
    private List<Product> getAllProduct() {
        return productService.getAllProduct();
    }    

    @GetMapping("/api/product/{id}")
    private Optional<Product> getProduct(@PathVariable("id") int id) {
        return productService.getProductById(id);
    }
    
    @PostMapping("/api/product")  
    private Product saveProduct(@RequestBody Product product)   
    {  
        productService.saveOrUpdate(product);  
        return product;  
    }

    @DeleteMapping("/api/product/{id}")
    private void deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
    }


}
