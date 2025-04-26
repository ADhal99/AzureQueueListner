package com.abd.azure.queue_listner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abd.azure.queue_listner.service.KeyVaultService;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class QueueListnerApplication {

	private final ServiceBusProcessorClient processorClient;
	private final ServiceBusSenderClient senderClient;
	private final KeyVaultService keyVaultService;

	public QueueListnerApplication(ServiceBusProcessorClient processorClient, KeyVaultService keyVaultService,
			ServiceBusSenderClient senderClient) {
		this.processorClient = processorClient;
		this.keyVaultService = keyVaultService;
		this.senderClient = senderClient;
	}

	String input = """
						{
			  "id": "TX004AD",
			  "street": "Patia Road",
			  "city": "BBSR",
			  "state": "Odisha",
			  "zipCode": "755017",
			  "country": "India"
			}
						""";

	public static void main(String[] args) {
		SpringApplication.run(QueueListnerApplication.class, args);
	}

	@PostConstruct
	public void startListner() {
		String secret = keyVaultService.fetchSecret("Name");
		log.info("Fetched Name from Key Vaulut :: " + secret);
		senderClient.sendMessage(new ServiceBusMessage(input));
		log.info("Message send.."+input);
		processorClient.start();
		log.info("Listner started...");
	}

	@PreDestroy
	public void stopListner() {
		processorClient.stop();
		log.info("Listner stoped...");
	}

}
