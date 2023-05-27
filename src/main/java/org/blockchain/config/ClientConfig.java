package org.blockchain.config;

import org.blockchain.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig {

    public static final int transactionsInBlock = 3;
    public static final String mining_difficulty = "00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    @Value("${addresses}")
    private String otherClientsAddresses;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(1);
    }

    public List<Client> getOtherClients() {
        return Arrays.stream(otherClientsAddresses.split(",")).map(Client::new).toList();
    }
}
