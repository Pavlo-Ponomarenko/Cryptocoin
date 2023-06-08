package org.blockchain.controllers;

import org.blockchain.dtos.CryptoKeyPair;
import org.blockchain.utils.CryptoKeyGenerator;
import org.blockchain.utils.Signing;
import org.blockchain.view.SigningForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CryptographyController {

    @GetMapping("get_key_pair")
    public ResponseEntity<CryptoKeyPair> sendNewKeyPair() {
        return new ResponseEntity<>(CryptoKeyGenerator.genNewPair(), HttpStatus.OK);
    }

    @PostMapping("sign_transaction")
    public ResponseEntity<String> signMessage(@RequestBody SigningForm signingForm) {
        return new ResponseEntity<>(Signing.sign(signingForm.getTransactionHash(), signingForm.getPrivateKey()), HttpStatus.OK);
    }
}
