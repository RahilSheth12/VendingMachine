package edu.vhhs.demo.vendingmachine.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", nullable = false, unique = true)
    private String name;

    @Column(name = "product_desc", nullable = true)
    private String description;

    @Column(name = "product_image", nullable = true)
    private String imageURL;

    @Column(name = "product_cost")
    private Float cost;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @IndexColumn(name = "INDEX_COL")
    @JsonIgnore
    private List<Inventory> inventories;

    public Product(String name, String desc, String image_url, float cost) {
        super();
        this.name = name;
        this.description = desc;
        this.imageURL = image_url;
        this.cost = cost;
    }
}
