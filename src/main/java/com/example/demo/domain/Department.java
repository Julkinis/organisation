package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer countStaff;
    private String rooms;

    @OneToOne
    @JoinColumn(name="boss_id")
    private Staff boss;
    public Department(){}
    public Department(String name, String rooms) {
        this.name=name;
        this.rooms=rooms;
        this.countStaff=0;
        this.boss=null;
    }
}
