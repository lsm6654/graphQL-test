package com.sumlee.graphql.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "age")
    int age;

    @ManyToOne
    @JoinColumn(name="schooId")
    private School school;
}
