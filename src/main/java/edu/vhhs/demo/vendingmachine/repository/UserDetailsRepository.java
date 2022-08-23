package edu.vhhs.demo.vendingmachine.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.vhhs.demo.vendingmachine.entity.UserDetails;

@Repository("userDetailsRepository")
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {

    Optional<UserDetails> findByEmail(String email);

}
