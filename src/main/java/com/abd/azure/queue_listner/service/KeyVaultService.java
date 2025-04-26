package com.abd.azure.queue_listner.service;

import org.springframework.stereotype.Service;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

@Service
public class KeyVaultService {


        private final SecretClient secretClient;

        public KeyVaultService(SecretClient secretClient){
            this.secretClient = secretClient;
        }

        public void addSecret(String secretName, String secretValue) {
            secretClient.setSecret(new KeyVaultSecret(secretName, secretValue));
        }

        public String fetchSecret(String secretName) {
            KeyVaultSecret secret = secretClient.getSecret(secretName);
            return secret.getValue();
        }
    
}
