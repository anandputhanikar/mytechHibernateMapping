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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	// private static final long serialVersionUID = -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", unique = true, nullable = false)
	private int userId;

	@Column(name = "USER_NAME", unique = false, nullable = false, length = 100)
	private String userName;
	/**(1)
	 * if will not add cascade then it will toss the below exception
	 * 
	 * “object references an unsaved transient instance - save the transient instance before flushing” error
	 */
	/*@OneToOne(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="VEHICLE_ID")
	private Vehicle vehicle;*/
	
	/**
	 * (point#2)when we do @OneToMany then extra table has been created to have index i.e user_details_vehicle with two columns
	 * `USER_DETAILS_USER_ID`, `vehicleList_VEHICLE_ID`
	 * 
	 * we can customize table name and columns name by using @JoinTable and JoinColumns for parent table's column and 
	 * inversJoinColumns for child table column
	 * 
	 * (point#2.1)
	 * if you have user then you can get all vehicles which user has- which you can do by creating list of vehicles in user object.
	 * if you know the vehicle then you can also find user name by 
	 * adding user column to vehicle object.
	 * So while saving the dat to db you have to set user in vehicle obj in main class
	 * 
	 * (point#2.2) 
	 * For list of vehicles will not be having vehicle ids but Vehicles can have single user
	 * so will user mappedBy to tell to which column should be mapped for this list vehicles in vehicle object.
	 * And this will not create separate table to have index instead it will create column in child object.
	 * 
	 * (point#2.3) in manyToOne there may be chances of getting notfound exception for eg: if vehicle has not having any user relationship 
	 * and when getting userdetail by vehicle object then it will user notfound exception. so we can ignore by annotating at child (vehicle) class alsong with manytoone annotation
	 * and also providing action ignore by enumeration.
	 * i.e @NotFound(action=NotFoundAction.IGNORE)
	 *
	 */
	
	/*(2.x)
	 * @OneToMany( mappedBy="user", cascade=javax.persistence.CascadeType.ALL)*/
	/*(2.1)
	 * @JoinTable(name="USER_VEHICLE",
		joinColumns=@JoinColumn(name="USER_ID"),
		inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))*/
	
	/**
	 * (point#3) Bidierectional relationship with manytomany eg: if user has rented vehicle, where he can more than one vehicle and same vehicles can be hired by other user too
	 * here it will create two mapping - it wont be knowing both are same so extra tables have been created for index which is redundant mapping.
	 * Hibernate: insert into USER_DETAILS_Vehicle (USER_DETAILS_USER_ID, vehicleList_VEHICLE_ID) values (?, ?)
	 * Hibernate: insert into Vehicle_USER_DETAILS (Vehicle_VEHICLE_ID, userList_USER_ID) values (?, ?)
	 * We can tell hibernate that parent class mapping to child at child also will say in the column of child mapped by column name in the parent class.
	 * SO here when parents get save then userId, vehilceId in it so will go to child class and will say it has mapped in parent.
	 * Since parent class is going to save so will give table and column/inverse column names in parent class only. 
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="USER_VEHICLE",
		joinColumns=@JoinColumn(name="USER_ID"),
		inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
	private Collection<Vehicle> vehicleList = new ArrayList();
	
	public Collection<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(Collection<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

}