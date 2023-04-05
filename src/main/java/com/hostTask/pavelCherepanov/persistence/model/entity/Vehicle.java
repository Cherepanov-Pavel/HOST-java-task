package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Vehicles table.
 */
@ToString
@Data
@Entity
@Table(name = "vehicle")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Vehicle {
	/**
	 * Primary key annotation.
	 */
	@Id
	/**
	 * To indicate the correspondence between the entity and the column of the database table, we specify the @column annotation and the name of the column in the DB in it.
	 */
	@Column(name = "guid")
	String guid;
	/**
	 * specifying a many-to-one relationship with the VehicleType table
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicleTypeID")
	VehicleType vehicletypej;

	/**
	 * specifying a many-to-one relationship with the Engine table
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "engineID")
	Engine enginej;

	/**
	 * specifying a many-to-one relationship with the Status table
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "statusID")
	Status statusj;

	/**
	 * specifying a many-to-one relationship with the MarqueModel table
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "marqueModelID")
	MarqueModel marqueModelj;

	@Column(name = "dateInsert")
	LocalDateTime dateInsert;

	@Column(name = "datePurchase")
	LocalDateTime datePurchase;

	@Column(name = "topSpeedMph")
	Integer topSpeedMph;

	@Column(name = "costUsd")
	Integer costUsd;

	@Column(name = "price")
	Integer price;
}
