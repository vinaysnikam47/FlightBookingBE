package com.infy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.infy.model.Status;

@Entity
@Table(name = "Flight")
public class FlightEntity {
	
	@Id
	private String flightId;
	private String aircraftName;
	private Double fare;
	private Integer availableSeats;
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "flightId")
	private List<BookingEntity> bookings;
	
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getAircraftName() {
		return aircraftName;
	}
	public void setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
	}
	public Double getFare() {
		return fare;
	}
	public void setFare(Double fare) {
		this.fare = fare;
	}
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<BookingEntity> getBookings() {
		return bookings;
	}
	public void setBookings(List<BookingEntity> bookings) {
		this.bookings = bookings;
	}
	
	
}
