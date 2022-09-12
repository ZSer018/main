package data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Guarantor {

    @Id
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person personId;

    @JoinColumn(name = "guarantor_id")
    @OneToOne
    private Person guarantorId;


    @Override
    public String toString() {
        return "Guarantor{" +
                "id=" + id +
                ", guarantorId=" + guarantorId +
                '}';
    }
}
