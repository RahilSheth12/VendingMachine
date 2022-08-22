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

import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.service.VendingMachineService;

@RestController
@RequestMapping(path = "/api/v${ApiVersion}/vendingMachine")
public class VendingMachineController {

    @Autowired
    VendingMachineService vendingMachineService;

    @GetMapping("")
    private List<VendingMachine> getAllVendingMachine() {
        return vendingMachineService.getAllVendingMachine();
    }

    @GetMapping("/{id}")
    private Optional<VendingMachine> getVendingMachine(@PathVariable("id") int id) {
        return vendingMachineService.getVendingMachineById(id);
    }

    @PostMapping("")
    private VendingMachine saveVendingMachine(@RequestBody VendingMachine vendingMachine) {
        vendingMachineService.saveOrUpdate(vendingMachine);
        return vendingMachine;
    }

    @DeleteMapping("/{id}")
    private void deleteVendingMachine(@PathVariable("id") int id) {
        vendingMachineService.delete(id);
    }
}
