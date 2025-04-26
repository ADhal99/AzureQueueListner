package com.abd.azure.queue_listner.listner;

import org.springframework.stereotype.Component;

import com.abd.azure.queue_listner.dto.Address;
import com.abd.azure.queue_listner.service.AddressService;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@Component
@Log
public class QueueListener {

    private final AddressService addressService;

    public QueueListener(AddressService addressService) {
        this.addressService = addressService;
    }

    public void processMessage(ServiceBusReceivedMessageContext context) {
        try {
            ServiceBusReceivedMessage message = context.getMessage();

            Address address = convertMessageToAddress(message.getBody().toString());

            addressService.saveAddress(address);

            context.complete();
            log.info("Message processed successfully: " + message.getMessageId());
        } catch (JsonMappingException e) {
            log.warning("Failed to convert message to Address DTO: " + e.getMessage());
            context.abandon();
        } catch (JsonProcessingException e) {
            log.warning("Failed to convert message to Address DTO: " + e.getMessage());
            context.abandon();
        } catch (IllegalArgumentException e) {
            log.warning("Invalid message format: " + e.getMessage());
            context.abandon();
        } catch (RuntimeException e) {
            log.warning("Failed to save address: " + e.getMessage());
            context.abandon();
        } catch (Exception e) {
            log.warning("Failed to process message: " + e.getMessage());
            context.abandon();
        }
    }

    public void processError(ServiceBusErrorContext context) {
        log.warning("Error occurred: " + context.getException().getMessage());
        log.warning("Error context: " + context.getErrorSource() + ", " + context.getFullyQualifiedNamespace() + ", "
                + context.getEntityPath());
    }

    private Address convertMessageToAddress(String messageBody) throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(messageBody, Address.class);
    }
}
