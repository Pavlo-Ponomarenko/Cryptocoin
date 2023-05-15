package org.blockchain.services;

import org.blockchain.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;
}
