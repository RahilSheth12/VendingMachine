package edu.vhhs.demo.vendingmachine.repository;

import org.springframework.data.repository.CrudRepository;

import edu.vhhs.demo.vendingmachine.entity.Product;

public interface ProductRepository extends CrudRepository <Product, Integer> {

    
    
}
