package com.infy.dao;

import java.util.ArrayList;

import com.infy.model.Booking;
import com.infy.model.Flight;

public interface FlightBookingDao {
	
	public ArrayList<Flight> getAllFlights();
	
	public ArrayList<Booking> getAllBookings();
	
	public String bookFlight(Booking bookingData);
	
	public Integer deleteBooking(Integer bookingId);
	
	public String updateBooking(Integer bookingId, Booking bookingData);
}	
