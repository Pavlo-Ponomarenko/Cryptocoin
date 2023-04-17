package org.blockchain.config;

import org.blockchain.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ClientConfig {

    @Value("${addresses}")
    private String otherClientsAddresses;

    public List<Client> getOtherClients() {
        return Arrays.stream(otherClientsAddresses.split(",")).map(Client::new).toList();
    }
}
