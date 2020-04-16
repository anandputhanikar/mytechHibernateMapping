package com.mytech.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="VEHICLE_ID", unique = true, nullable = false)
	private int vehicleId;
	
	@Column(name="VEHICLE_NAME")
	private String vehicleName;
	/**
	 * (point#2.2)- description given in userDetails class
	 * this is biderectional relationship
	 * (point#2.3)-ignore not found exception when fetching user by vehicle object.
	 * @return
	 */
	/*@ManyToOne
	@JoinColumn(name="USER_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private UserDetails user;*/
	
	@ManyToMany(mappedBy="vehicleList",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	
	private Collection<UserDetails> userList = new ArrayList();
	
	
	public Collection<UserDetails> getUserList() {
		return userList;
	}

	public void setUserList(Collection<UserDetails> userList) {
		this.userList = userList;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	
	
}
