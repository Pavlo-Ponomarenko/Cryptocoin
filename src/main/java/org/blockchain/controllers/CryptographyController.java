package org.blockchain.controllers;

import org.blockchain.dtos.CryptoKeyPair;
import org.blockchain.utils.CryptoKeyGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CryptographyController {

    @GetMapping("get_key_pair")
    public ResponseEntity<CryptoKeyPair> sendNewKeyPair() {
        return new ResponseEntity<>(CryptoKeyGenerator.genNewPair(), HttpStatus.OK);
    }
}
