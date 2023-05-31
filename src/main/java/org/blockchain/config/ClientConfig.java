package org.blockchain.config;

import org.blockchain.client.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig implements CommandLineRunner {

    public static final int transactionsInBlock = 3;
    public static final String mining_difficulty = "00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    private List<Client> clients;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(1);
    }

    public List<Client> getOtherClients() {
        return clients;
    }

    @Override
    public void run(String... args) {
        clients = new ArrayList<>();
        for (String address : args) {
            clients.add(new Client(address));
        }
    }
}
