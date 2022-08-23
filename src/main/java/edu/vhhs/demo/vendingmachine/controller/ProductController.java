package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;
import edu.vhhs.demo.vendingmachine.service.ProductService;

@RestController
@RequestMapping(path = "/api/v${ApiVersion}/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Product> getAllProduct() {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // LOGGER.info(auth.getAuthorities().toString());
        return productService.getAllProduct();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Product getProduct(@PathVariable("id") int id) {
        Optional<Product> opProduct = productService.getProductById(id);
        Product product = opProduct.isPresent() ? opProduct.get() : new Product();
        return product;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_ROLE")))
            productService.saveOrUpdate(product);
        else
            return new ResponseEntity<Product>(product, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private void deleteProduct(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_ROLE")))
            productService.delete(id);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Retrieve all products by vending machine id
     * 
     * @param location_id vending machine id
     * @return List<ProductJoin> list of products with available inventories and
     *         vending machine name
     */
    @GetMapping(value = "/location/{location_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<ProductJoin> getAvailableProductsByLocation(@PathVariable("location_id") int location_id) {
        return productService.getAvailableProductsByLocation(location_id);
    }

}
