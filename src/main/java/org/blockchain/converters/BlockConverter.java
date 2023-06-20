package org.blockchain.converters;

import org.blockchain.dtos.Block;
import org.blockchain.entities.BlockRecord;

import java.util.List;

public interface BlockConverter extends Converter<Block, BlockRecord> {

    List<Block> entitiesToBlocks(List<BlockRecord> blockRecords);
}
