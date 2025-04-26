package com.abd.azure.queue_listner.config;

import org.springframework.context.annotation.Configuration;

import com.abd.azure.queue_listner.listner.QueueListener;
import com.azure.core.credential.TokenCredential;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class ListnerConfig {

    @Value("${spring.cloud.azure.servicebus.namespace}")
    private String namespaceName;

    @Value("${spring.cloud.azure.servicebus.entity-name}")
    private String queueName;

    private TokenCredential tokenCredential;
    private QueueListener queueListener;

    public ListnerConfig(TokenCredential tokenCredential, QueueListener queueListener) {
        this.tokenCredential = tokenCredential;
        this.queueListener = queueListener;
    }

    @Bean
    public ServiceBusProcessorClient serviceBusProcessorClient() {
        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(namespaceName)
                .credential(tokenCredential)
                .processor()
                .disableAutoComplete()
                .queueName(queueName)
                .processMessage(queueListener::processMessage)
                .processError(queueListener::processError)
                .buildProcessorClient();
    }

}
