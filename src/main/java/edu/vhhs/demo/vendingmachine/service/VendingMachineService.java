package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

public class VendingMachineService {

    @Autowired
    VendingMachineRepository vendingMachineRepository;

    public List<VendingMachine> getAllVendingMachine() {
        List<VendingMachine> vendingMachines = new ArrayList<VendingMachine>();
        vendingMachineRepository.findAll().forEach(vendingMachine -> vendingMachines.add(vendingMachine));
        return vendingMachines;
    }

    public Optional<VendingMachine> getVendingMachineById(int id) {
        return vendingMachineRepository.findById(id);
    }

    public void saveOrUpdate(VendingMachine vendingMachine) {
        vendingMachineRepository.save(vendingMachine);
    }

    public void delete(int id) {
        vendingMachineRepository.deleteById(id);
    }

    // public Inventory getInventoryByProductID(int product_id) {
    // return inventoryRepository.findByProductId(product_id);
    // }

    // public List<Inventory> getInventoryByLocation(int location_id) {
    // return inventoryRepository.findByLocation(location_id);
    // }

}
