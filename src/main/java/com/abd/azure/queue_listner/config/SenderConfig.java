package com.abd.azure.queue_listner.config;

import org.springframework.context.annotation.Configuration;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.core.credential.TokenCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class SenderConfig {

    private final TokenCredential tokenCredential;

    public SenderConfig(TokenCredential tokenCredential) {
        this.tokenCredential = tokenCredential;
    }

    @Value("${spring.cloud.azure.servicebus.entity-name}")
    private String queueName;

    @Value("${spring.cloud.azure.servicebus.namespace}")
    private String namespaceName;

    @Bean
    public ServiceBusSenderClient serviceBusSenderClient() {
        return new ServiceBusClientBuilder()
                .credential(namespaceName, tokenCredential)
                .sender()
                .queueName(queueName)
                .buildClient();
    }

}
