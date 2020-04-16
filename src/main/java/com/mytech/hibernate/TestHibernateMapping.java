package com.mytech.hibernate;

import org.hibernate.Session;

import com.mytech.hibernate.dto.UserDetails;
import com.mytech.hibernate.dto.Vehicle;

public class TestHibernateMapping {

	public static void main(String[] args) {

		Session session = HibernateUtils.getSessionFactory().openSession();
		
		
		UserDetails userDetails = new UserDetails();
		userDetails.setUserName("Gnili");
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("car");
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleName("Bike");
		
		userDetails.getVehicleList().add(vehicle);
		userDetails.getVehicleList().add(vehicle2);
		
		
		vehicle.getUserList().add(userDetails);
		vehicle2.getUserList().add(userDetails);
		session.beginTransaction();
		session.save(userDetails);
		session.getTransaction().commit();
		session.close();
		HibernateUtils.shutodown();
	}

}
