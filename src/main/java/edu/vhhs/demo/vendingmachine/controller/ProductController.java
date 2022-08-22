package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.service.ProductService;

@RestController
@RequestMapping(path = "/api/v${ApiVersion}/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    private List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    private Optional<Product> getProduct(@PathVariable("id") int id) {
        return productService.getProductById(id);
    }

    @PostMapping("")
    private Product saveProduct(@RequestBody Product product) {
        productService.saveOrUpdate(product);
        return product;
    }

    @DeleteMapping("/{id}")
    private void deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
    }

    /**
     * Retrieve all products by vending machine id
     * 
     * @param location_id vending machine id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    @GetMapping("/location/{location_id}")
    private List<ProductJoin> getAvailableProductsByLocation(@PathVariable("location_id") int location_id) {
        return productService.getAvailableProductsByLocation(location_id);
    }

}
