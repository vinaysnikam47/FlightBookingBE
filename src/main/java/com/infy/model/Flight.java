package com.infy.model;

import java.util.List;

public class Flight {
	
	private String flightId;
	private String aircraftName;
	private Double fare;
	private Integer availableSeats;
	private Status status;
	private List<Booking> bookings;
	
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
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	
}
