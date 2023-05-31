package org.blockchain.controllers;

import org.blockchain.client.BlockProcessor;
import org.blockchain.dtos.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BlockController {
    @Autowired
    private BlockProcessor blockProcessor;

    @PostMapping("send_block")
    public ResponseEntity<Void> receiveBlock(@RequestBody Block block) {
        blockProcessor.validateForeignBlock(block);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
