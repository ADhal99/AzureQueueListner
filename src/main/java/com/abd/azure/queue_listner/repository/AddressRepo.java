package com.abd.azure.queue_listner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abd.azure.queue_listner.dto.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, String> {
    

}
