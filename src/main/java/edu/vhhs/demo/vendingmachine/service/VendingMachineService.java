package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

public class VendingMachineService {

    @Autowired
    VendingMachineRepository vendingMachineRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public List<VendingMachine> getAllVendingMachine() {
        List<VendingMachine> vendingMachines = new ArrayList<VendingMachine>();
        vendingMachineRepository.findAll().forEach(vendingMachine -> vendingMachines.add(vendingMachine));
        return vendingMachines;
    }

    public Optional<VendingMachine> getVendingMachineById(int id) {
        return vendingMachineRepository.findById(id);
    }

    public VendingMachine saveOrUpdate(VendingMachine vendingMachine) {
        return vendingMachineRepository.save(vendingMachine);
    }

    /**
     * Delete all inventories for the vending machine across all product before
     * deleting vending machine
     * 
     * @param id vending machine id
     */
    public void delete(int id) {
        VendingMachine vendingMachine = getVendingMachineById(id).isPresent() ? getVendingMachineById(id).get()
                : new VendingMachine();
        List<Inventory> inventories = inventoryRepository.findByVendingMachine(vendingMachine);
        ArrayList<Inventory> updatedInventories = new ArrayList<Inventory>();
        for (Inventory inventory : inventories) {
            inventory.setProduct(null);
            inventory.setVendingMachine(null);
            updatedInventories.add(inventory);
        }
        inventoryRepository.deleteAll(updatedInventories);
        vendingMachineRepository.delete(vendingMachine);
    }
}
