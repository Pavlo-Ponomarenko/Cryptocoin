package org.blockchain.client;

import org.blockchain.dtos.Block;
import org.blockchain.dtos.Transaction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Client {

    private final String address;

    public Client(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public ResponseEntity<String> sendTransaction(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transaction> requestEntity = new HttpEntity<>(transaction, headers);
        return restTemplate.postForEntity(address + "/send_transaction", requestEntity, String.class);
    }

    public ResponseEntity<String> sendBlock(Block block) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Block> requestEntity = new HttpEntity<>(block, headers);
        return restTemplate.postForEntity(address + "/send_block", requestEntity, String.class);
    }
}
