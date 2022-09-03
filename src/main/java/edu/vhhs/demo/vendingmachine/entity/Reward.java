package edu.vhhs.demo.vendingmachine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rewards")
@Getter
@Setter
@NoArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer points;

    @Column
    @Enumerated(EnumType.STRING)
    private RewardAction action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private UserProfile studentProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private UserProfile teacherProfile;
}
