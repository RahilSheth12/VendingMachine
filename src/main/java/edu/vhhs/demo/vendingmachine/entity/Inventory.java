package edu.vhhs.demo.vendingmachine.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    // @Column(name = "location_id", nullable = false)
    // private Integer location;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "vending_machine_id", nullable = false, unique = true)
    @JsonIgnore
    private VendingMachine vendingMachine;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "product_id", nullable = false, unique = true)
    @JsonIgnore
    private Product product;

    // public Inventory(Integer count, Integer location) {
    // super();
    // this.count = count;
    // this.location = location;
    // }
    public Inventory(Integer count) {
        super();
        this.count = count;
    }

}
