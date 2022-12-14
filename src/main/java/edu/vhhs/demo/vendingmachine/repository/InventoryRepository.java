package edu.vhhs.demo.vendingmachine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.Product;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.projection.ProductJoin;

@Repository("inventoryRepository")
@Transactional
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    @Query(value = "SELECT distinct p.id as id, p.product_name as name, p.product_desc as description, p.product_image as imageURL, p.product_cost as cost, i.inventory_count as count, v.name as locationName from products p left outer join inventories i on p.id = i.product_id left outer join vending_machines v on i.vending_machine_id=v.id where i.inventory_count is not null and v.name is not null and p.id=?1", nativeQuery = true)
    List<ProductJoin> findAllInventoryByProductIdNative(Integer product_id);

    List<Inventory> findByProduct(Product product);

    List<Inventory> findByVendingMachine(VendingMachine vendingMachine);
}
