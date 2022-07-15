package com.infy.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.model.Booking;
import com.infy.model.Flight;
import com.infy.model.StringResponse;
import com.infy.service.FlightBookingService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/flights")
public class FlightBookingApi {
	
	@Autowired
	private FlightBookingService flightBookingService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping(value = "/")
	public ResponseEntity<ArrayList<Flight>> getAllFlights(){
		try {
			ArrayList<Flight> flightList = flightBookingService.getAllFlights();
			ResponseEntity<ArrayList<Flight>> response = new ResponseEntity<ArrayList<Flight>>(flightList, HttpStatus.OK);
			return response;
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping(value = "/book")
	public ResponseEntity<StringResponse> bookFlight(@RequestBody Booking bookingData){
		try {
			String value = flightBookingService.bookFlight(bookingData);
			StringResponse stringResponse = new StringResponse();
			stringResponse.setResponse(environment.getProperty("Api.BOOKING_SUCCESSFUL") + value);
			ResponseEntity<StringResponse> response = new ResponseEntity<StringResponse>(stringResponse, HttpStatus.OK);
			return response;
		}
		catch (Exception e) {
			if(e.getMessage().startsWith("Sorry") || e.getMessage().startsWith("Insufficient")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			}
		}
	}
	
	@DeleteMapping(value = "/delete/{bookingId}")
	public ResponseEntity<StringResponse> deleteBooking(@PathVariable(value = "bookingId") Integer bookingId){
		try {
			Integer id = flightBookingService.deleteBooking(bookingId);
			StringResponse stringResponse = new StringResponse();
			stringResponse.setResponse(environment.getProperty("Api.DELETE_SUCCESS") + id);
			ResponseEntity<StringResponse> response = new ResponseEntity<StringResponse>(stringResponse, HttpStatus.OK);
			return response;
		}
		catch (Exception e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	@PutMapping(value = "update/{bookingId}")
	public ResponseEntity<StringResponse> updateBooking(@PathVariable(value = "bookingId") Integer bookingId, @RequestBody Booking bookingData){
		try {
			String id = flightBookingService.updateBooking(bookingId, bookingData);
			StringResponse stringResponse = new StringResponse();
			stringResponse.setResponse(environment.getProperty("Api.UPDATE_SUCCESSFUl") + id);
			ResponseEntity<StringResponse> resposne = new ResponseEntity<StringResponse>(stringResponse, HttpStatus.OK);
			return resposne;
		}
		catch (Exception e) {
			if(e.getMessage().startsWith("Sorry") || e.getMessage().startsWith("Insufficient")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			}
		}
	}
	
	@GetMapping(value = "/bookings")
	public ResponseEntity<ArrayList<Booking>> getAllBookings(){
		try {
			ArrayList<Booking> bookigList = flightBookingService.getAllBookings();
			ResponseEntity<ArrayList<Booking>> response = new ResponseEntity<ArrayList<Booking>>(bookigList, HttpStatus.OK);
			return response;
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}
