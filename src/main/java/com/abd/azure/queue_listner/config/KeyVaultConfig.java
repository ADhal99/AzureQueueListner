package com.abd.azure.queue_listner.config;

import org.springframework.context.annotation.Configuration;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.core.credential.TokenCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class KeyVaultConfig {

    private final TokenCredential tokenCredential;

    @Value("${azure.keyvault.url}")
    private String keyVaultUrl;

    public KeyVaultConfig(TokenCredential tokenCredential) {
        this.tokenCredential = tokenCredential;
    }

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(tokenCredential)
                .buildClient();
    }

}
