package com.example.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder


public class Complaint implements Serializable, Cloneable {
    private int id;
    private String name;
    private String description;
    private String status;

    public Complaint(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
