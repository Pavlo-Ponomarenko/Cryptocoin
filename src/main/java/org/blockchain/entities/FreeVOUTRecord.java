package org.blockchain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "free_vout")
public class FreeVOUTRecord {
    @Id
    private Long id;

    public FreeVOUTRecord() {
    }

    public FreeVOUTRecord(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
