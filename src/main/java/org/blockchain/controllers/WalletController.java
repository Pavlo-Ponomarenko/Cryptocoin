package org.blockchain.controllers;

import org.blockchain.dtos.CryptoKeyPair;
import org.blockchain.utils.CryptoKeyGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WalletController {

    @GetMapping("menu")
    public String getMenu() {
        return "Menu.html";
    }

    @GetMapping("wallet")
    public String getWallet(Model model, @RequestParam(value = "key") String key) {
        model.addAttribute("address", key);
        return "Wallet.html";
    }

    @GetMapping("new_keys_page")
    public String getNewKeysPage(Model model) {
        CryptoKeyPair pair = CryptoKeyGenerator.genNewPair();
        model.addAttribute("PublicKey", pair.getPublicKey());
        model.addAttribute("PrivateKey", pair.getPrivateKey());
        return "NewKeys.html";
    }
}
