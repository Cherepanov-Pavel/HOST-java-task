package com.hostTask.pavelCherepanov.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle engines table.
 */
@ToString
@Data
@Entity
@Table(name = "engine")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Engine {
    /**
     * Primary key annotation.
     */
    @Id
    /**
     * This annotation indicate that we want the ID to be generated automatically when adding a new record.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * To indicate the correspondence between the entity and the column of the database table, we specify the @column annotation and the name of the column in the DB in it
     */
    @Column(name = "engineID")
    Integer engineID;
    @Column(name = "engine")
    String engine;
    @Column(name = "enginePowerBhp")
    Integer enginePowerBhp;

    /**
     * specifying a one-to-many relationship with the vehicle table
     */
    @OneToMany(mappedBy = "enginej", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}