package com.sumlee.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table
@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "type")
    String type;

    @Column(name = "location")
    String location;

    @OneToMany(mappedBy = "school")
    private List<Student> students;

    public School() {

    }

    public School(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }
}
