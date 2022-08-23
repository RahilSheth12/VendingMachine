package edu.vhhs.demo.vendingmachine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

import edu.vhhs.demo.vendingmachine.entity.VendingMachine;
import edu.vhhs.demo.vendingmachine.service.VendingMachineService;

@RestController
@RequestMapping(path = "/api/v${ApiVersion}/vendingMachine")
public class VendingMachineController {

    @Autowired
    @Qualifier("vendingMachineService")
    private VendingMachineService vendingMachineService;

    @GetMapping("")
    private List<VendingMachine> getAllVendingMachine() {
        return vendingMachineService.getAllVendingMachine();
    }

    @GetMapping("/{id}")
    private VendingMachine getVendingMachine(@PathVariable("id") int id) {
        Optional<VendingMachine> opVendingMachine = vendingMachineService.getVendingMachineById(id);
        VendingMachine vendingMachine = opVendingMachine.isPresent() ? opVendingMachine.get() : new VendingMachine();
        return vendingMachine;
    }

    @PostMapping("")
    private ResponseEntity<VendingMachine> saveVendingMachine(@RequestBody VendingMachine vendingMachine) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_ROLE")))
            vendingMachineService.saveOrUpdate(vendingMachine);
        else
            return new ResponseEntity<VendingMachine>(vendingMachine, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<VendingMachine>(vendingMachine, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private void deleteVendingMachine(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_ROLE")))
            vendingMachineService.delete(id);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
