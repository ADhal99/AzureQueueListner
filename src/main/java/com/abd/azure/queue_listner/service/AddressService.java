package com.abd.azure.queue_listner.service;

import org.springframework.stereotype.Service;

import com.abd.azure.queue_listner.dto.Address;
import com.abd.azure.queue_listner.dto.ResponseData;
import com.abd.azure.queue_listner.repository.AddressRepo;

@Service
public class AddressService {

    private AddressRepo addressRepo;

    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public ResponseData saveAddress(Address address) {
        try {
            Address savedAddress = addressRepo.save(address);
            return ResponseData.builder()
                               .status("SUCCESS")
                               .message("Address saved successfully with ID: " + savedAddress.getId())
                               .build();
                               
        } catch (Exception e) {
            return ResponseData.builder()
                               .status("FAILURE")
                               .message("Failed to save address: " + e.getMessage())
                               .build();
        }
    }
}
