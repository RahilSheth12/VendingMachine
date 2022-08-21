package edu.vhhs.demo.vendingmachine.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.vhhs.demo.vendingmachine.entity.Inventory;

public interface InventoryRepository extends CrudRepository <Inventory, Integer> {

    Inventory findByProductId(Integer id);

    List<Inventory> findByLocation(int location_id);

    void deleteByProductId(Integer id);
    
}
