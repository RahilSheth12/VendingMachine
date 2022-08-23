package edu.vhhs.demo.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.vhhs.demo.vendingmachine.entity.Inventory;
import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.repository.InventoryRepository;
import edu.vhhs.demo.vendingmachine.repository.VendingMachineRepository;

@Service("vendingMachineService")
public class VendingMachineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendingMachineService.class);
    @Autowired
    @Qualifier("vendingMachineRepository")
    VendingMachineRepository vendingMachineRepository;

    @Autowired
    @Qualifier("inventoryRepository")
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
        LOGGER.debug("Delete vending machine({0} and all inventories associated with it.)", id);
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
