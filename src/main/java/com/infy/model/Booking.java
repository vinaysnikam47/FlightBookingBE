package com.infy.model;

public class Booking {
	
	private Integer bookingId;
	private String passengerName;
	private Integer noOfTickets;
	private Double bookingCost;
	private String customerId;
	private String flightId;
	
	
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Integer getNoOfTickets() {
		return noOfTickets;
	}
	public void setNoOfTickets(Integer noOfTickets) {
		this.noOfTickets = noOfTickets;
	}
	public Double getBookingCost() {
		return bookingCost;
	}
	public void setBookingCost(Double bookingCost) {
		this.bookingCost = bookingCost;
	}
	
	
}
