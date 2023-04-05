package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Table with marque and model ids.
 */
@ToString
@Data
@Entity
@Table(name = "marqueModel")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MarqueModel {
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
    @Column(name = "marqueModelID")
    Integer marqueModelID;
    /**
     * specifying a many-to-one relationship with the marque table
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "marqueID")
    Marque marquej;
    /**
     * specifying a many-to-one relationship with the model table
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelID")
    Model modelj;
    /**
     * specifying a one-to-many relationship with the vehicle table
     */
    @OneToMany(mappedBy = "marqueModelj", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}

