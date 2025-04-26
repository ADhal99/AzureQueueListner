package com.abd.azure.queue_listner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;

@Configuration
public class AzureCredentialConfig {

        @Value("${azure.client-id}") 
        private String clientId;
        @Value("${azure.client-secret}") 
        private String clientSecret;
        @Value("${azure.tenant-id}") 
        private String tenantId;

        @Value("${azure.mi.client-id}") 
        private String mi_clientId;

        @Value("${app.environment.local}") 
        private String isLocal;


    @Bean
    @Primary
    public TokenCredential clientSecretCredential(){

        if (isLocal.equalsIgnoreCase("true")) {
            return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
        } else {
            return new DefaultAzureCredentialBuilder()
                .managedIdentityClientId(mi_clientId)
                .build();
        }
    }
       
}
