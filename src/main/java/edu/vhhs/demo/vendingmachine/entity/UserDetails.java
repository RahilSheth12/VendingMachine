package edu.vhhs.demo.vendingmachine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_profile")
@Getter
@Setter
@NoArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "picture", nullable = true)
    private String picture;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserDetails(String name, String email, String picture) {
        super();
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

}
