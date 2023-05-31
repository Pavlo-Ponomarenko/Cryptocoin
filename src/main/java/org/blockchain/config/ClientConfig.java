package org.blockchain.config;

import org.blockchain.client.BlockProcessor;
import org.blockchain.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig implements CommandLineRunner {

    public static final int transactionsInBlock = 1;
    public static final String mining_difficulty = "00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    private List<Client> clients;
    @Autowired
    private BlockProcessor blockProcessor;

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
        ExecutorService executorService = executorService();
        executorService.submit(blockProcessor::run);
    }
}
