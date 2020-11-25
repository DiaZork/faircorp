package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Room;

public class RoomDto {
    private Long id;
    private Integer level;
    private String name;
    private String type;
    private Double currentTemperature;
    private Double targetTemperature;

    public RoomDto() {
    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.level = room.getLevel();
        this.name = room.getName();
        this.type = room.getType();
        this.currentTemperature = room.getCurrentTemperature();
        this.targetTemperature = room.getTargetTemperature();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
}
