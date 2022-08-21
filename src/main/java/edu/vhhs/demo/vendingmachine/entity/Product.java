package edu.vhhs.demo.vendingmachine.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private Inventory inventory;

    public Product(String name, String desc, String image_url, float cost) {
        super();
        this.name = name;
        this.description = desc;
        this.imageURL = image_url;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", image=" + imageURL
                + ", cost=" + cost + ", inventory=" + inventory + "]";
    }
}
