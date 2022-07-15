package com.infy.service;

import java.util.ArrayList;

import com.infy.model.Booking;
import com.infy.model.Flight;

public interface FlightBookingService {
	
	public ArrayList<Flight> getAllFlights() throws Exception;
	
	public ArrayList<Booking> getAllBookings() throws Exception;
	
	public String bookFlight(Booking bookingData) throws Exception;
	
	public Integer deleteBooking(Integer bookingId) throws Exception;
	
	public String updateBooking(Integer bookingId, Booking bookingData) throws Exception;
}
