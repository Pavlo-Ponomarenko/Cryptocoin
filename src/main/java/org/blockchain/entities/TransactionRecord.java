package org.blockchain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "transactions_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @OneToMany(mappedBy = "transaction")
    private List<VINRecord> vins;
    @OneToMany(mappedBy = "transaction")
    private List<VOUTRecord> vouts;
    @ManyToOne
    private BlockRecord block;

    public TransactionRecord() {
    }

    public TransactionRecord(Long id, List<VINRecord> vins, List<VOUTRecord> vouts, BlockRecord block) {
        this.id = id;
        this.vins = vins;
        this.vouts = vouts;
        this.block = block;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<VINRecord> getVins() {
        return vins;
    }

    public void setVins(List<VINRecord> vins) {
        this.vins = vins;
    }

    public List<VOUTRecord> getVouts() {
        return vouts;
    }

    public void setVouts(List<VOUTRecord> vouts) {
        this.vouts = vouts;
    }

    public BlockRecord getBlock() {
        return block;
    }

    public void setBlock(BlockRecord block) {
        this.block = block;
    }
}
