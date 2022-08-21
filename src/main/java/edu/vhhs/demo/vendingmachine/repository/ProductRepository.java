package edu.vhhs.demo.vendingmachine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.ProductJoin;

public interface ProductRepository extends CrudRepository <Product, Integer> {

    @Query(
        value = "SELECT p.id as id, p.product_name as name, p.product_desc as description, p.product_image as imageURL, p.product_cost as cost, i.inventory_count as count, i.location_id as location from inventories i inner join products p on i.product_id=p.id where i.location_id=?1",
        nativeQuery = true
    )
    List<ProductJoin> findAllProductJoin(Integer location_id);
    
}
