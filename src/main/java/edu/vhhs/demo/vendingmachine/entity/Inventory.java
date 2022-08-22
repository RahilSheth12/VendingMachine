package edu.vhhs.demo.vendingmachine.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inventory_count", nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private VendingMachine vendingMachine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Product product;

    public Inventory(Integer count) {
        super();
        this.count = count;
    }

}
