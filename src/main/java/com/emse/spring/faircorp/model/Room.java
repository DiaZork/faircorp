package com.emse.spring.faircorp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Room {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String name;

    @Column
    private Double currentTemperature;

    @Column
    private Double targetTemperature;

    @Column
    @OneToMany
    List<Heater> heaters;

    @OneToMany
    List<Window> windows;

    public Room(Integer floor, String name) {
        this.floor = floor;
        this.name = name;
    }

    public Room() {
    }
}
