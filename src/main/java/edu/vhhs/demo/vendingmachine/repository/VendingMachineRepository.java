package edu.vhhs.demo.vendingmachine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.vhhs.demo.vendingmachine.entity.VendingMachine;

@Repository
@Transactional
public interface VendingMachineRepository extends CrudRepository<VendingMachine, Integer> {
}
