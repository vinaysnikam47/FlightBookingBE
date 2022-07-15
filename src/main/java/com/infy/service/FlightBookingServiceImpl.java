package com.infy.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.dao.FlightBookingDao;
import com.infy.model.Booking;
import com.infy.model.Flight;

@Service(value = "FlightBookingService")
@Transactional
public class FlightBookingServiceImpl implements FlightBookingService{
	
	@Autowired
	private FlightBookingDao flightBookingDao;

	@Override
	public ArrayList<Flight> getAllFlights() throws Exception {
		ArrayList<Flight> flightList = flightBookingDao.getAllFlights();
		if(flightList.isEmpty()) {
			throw new Exception("Service.NO_FLIGHTS_FOUND");
		}
		else {
			return flightList;
		}
	}

	@Override
	public String bookFlight(Booking bookingData) throws Exception {
		String value = flightBookingDao.bookFlight(bookingData);
		if(value.equals("NCF")) {
			throw new Exception("Service.CUSTOMER_NOT_FOUND");
		}
		else if(value.startsWith("NSL")) {
			String seatsLeft = value.substring(3);
			throw new Exception("Sorry flight almost full... Only " + seatsLeft + " left!!");
		}
		else if(value.equals("0")) {
			throw new Exception("Service.NO_SEATS_AVAILABLE");
		}
		else if(value.startsWith("IA")) {
			String amount = value.substring(2);
			throw new Exception("Insufficient Wallet Amount! Add more Rs. " + amount + " to continue booking");
		}
		else if(value.startsWith("FC")) {
			String flight = value.substring(2);
			throw new Exception("Sorry for Inconvenience..." + flight + " is Cancelled!");
		}
		else {
			return value;
		}
	}

	@Override
	public Integer deleteBooking(Integer bookingId) throws Exception {
		Integer value = flightBookingDao.deleteBooking(bookingId);
		if(value == -1) {
			throw new Exception("Service.NO_BOOKING_FOUND");
		}
		else if(value == 0) {
			throw new Exception("Service.AMOUNT_NOT_LESS_THAN_3000");
		}
		else {
			return value;
		}
	}

	@Override
	public String updateBooking(Integer bookingId, Booking bookingData) throws Exception {
		String value = flightBookingDao.updateBooking(bookingId, bookingData);
		if(value.equals("NBF")) {
			throw new Exception("Service.NO_BOOKING_FOUND");
		}
		else if(value.equals("CNT")) {
			throw new Exception("Service.NO_TICKETS_CHANGE");
		}
		else if(value.equals("0")) {
			throw new Exception("Service.FLIGHT_FULL");
		}
		else if(value.startsWith("NSL")) {
			String seatsLeft = value.substring(3);
			throw new Exception("Sorry flight almost full... Only " + seatsLeft + " left!!");
		}
		else if(value.startsWith("IA")) {
			String amount = value.substring(2);
			throw new Exception("Insufficient Wallet Amount! Add more Rs. " + amount + " to continue booking");
		}
		else {
			return value;
		}
	}

	@Override
	public ArrayList<Booking> getAllBookings() throws Exception {
		ArrayList<Booking> bookingList = flightBookingDao.getAllBookings();
		if(bookingList.isEmpty()) {
			throw new Exception("Service.NO_BOOKINGS_FOUND");
		}
		else {
			return bookingList;
		}
	}

}
