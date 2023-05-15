package org.blockchain.converters;

import org.blockchain.dtos.Transaction;
import org.blockchain.entities.TransactionRecord;

public interface TransactionConverter extends Converter<Transaction, TransactionRecord> {}
