package edu.vhhs.demo.vendingmachine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;

@Repository("productRepository")
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "SELECT distinct p.id as id, p.product_name as name, p.product_desc as description, p.product_image as imageURL, p.product_cost as cost, i.inventory_count as count, v.name as locationName from products p left outer join inventories i on p.id = i.product_id left outer join vending_machines v on i.vending_machine_id=v.id where v.id=?1", nativeQuery = true)
    List<ProductJoin> findAllProductJoin(Integer location_id);

}
