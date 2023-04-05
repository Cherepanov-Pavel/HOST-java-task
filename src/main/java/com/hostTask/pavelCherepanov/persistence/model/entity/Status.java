package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle statuses table.
 */
@ToString
@Data
@Entity
@Table(name = "status")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Status {
    /**
     * Primary key annotation.
     */
    @Id
    /**
     * This annotation indicate that we want the ID to be generated automatically when adding a new record.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * To indicate the correspondence between the entity and the column of the database table, we specify the @column annotation and the name of the column in the DB in it.
     */
    @Column(name = "statusID")
    Integer statusID;
    @Column(name = "status")
    String status;
    /**
     * specifying a one-to-many relationship with the vehicle table
     */
    @OneToMany(mappedBy = "statusj", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}

