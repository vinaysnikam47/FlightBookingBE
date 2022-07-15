package com.infy.service;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.infy.dao.FlightBookingDao;
import com.infy.model.Booking;
import com.infy.model.Flight;
import com.infy.model.Status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightBookingServiceImplTest {
	
	@Mock
	FlightBookingDao flightBookingDao;
	
	@InjectMocks
	FlightBookingServiceImpl flightBookingService = new FlightBookingServiceImpl();
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Test
	public void getAllFlightsInvalidTest() throws Exception{
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		Mockito.when(flightBookingDao.getAllFlights()).thenReturn(flightList);
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_FLIGHTS_FOUND");
		flightBookingService.getAllFlights();
	}
	
	@Test
	public void getAllFlightsValidTest() throws Exception{
		ArrayList<Flight> flightListFromDao = new ArrayList<Flight>();
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		Flight f = new Flight();
		f.setAircraftName("Jet Blue");
		f.setAvailableSeats(10);
		f.setFare(1000d);
		f.setFlightId("IND-104");
		f.setStatus(Status.Running);
		f.setBookings(bookings);
		flightListFromDao.add(f);
		Mockito.when(flightBookingDao.getAllFlights()).thenReturn(flightListFromDao);
		ArrayList<Flight> actualFlightList = flightBookingService.getAllFlights();
		Assert.assertEquals(1, actualFlightList.size());
	}
	
	@Test
	public void bookFlightInvalidCustomerTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("NCF");
		ee.expect(Exception.class);
		ee.expectMessage("Service.CUSTOMER_NOT_FOUND");
		flightBookingService.bookFlight(b);
	}
	
	@Test
	public void bookFlightFlightAlmostFullTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("NSL1");
		ee.expect(Exception.class);
		ee.expectMessage("Sorry flight almost full... Only 1 left!!");
		flightBookingService.bookFlight(b);
	}
	
	@Test
	public void bookFlightNoSeatsAvailableTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("0");
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_SEATS_AVAILABLE");
		flightBookingService.bookFlight(b);
	}
	
	@Test
	public void bookFlightInsufficientAmountTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("IA300");
		ee.expect(Exception.class);
		ee.expectMessage("Insufficient Wallet Amount! Add more Rs. 300 to continue booking");
		flightBookingService.bookFlight(b);
	}
	
	@Test
	public void bookFlightFlightCancelledTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("FCIND-101");
		ee.expect(Exception.class);
		ee.expectMessage("Sorry for Inconvinience...IND-101 is Cancelled!");
		flightBookingService.bookFlight(b);
	}
	
	@Test
	public void bookFlightValidTest() throws Exception{
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		Mockito.when(flightBookingDao.bookFlight(b)).thenReturn("2008");
		Assert.assertEquals("2008", flightBookingService.bookFlight(b));
	}
	
	@Test
	public void deleteBookingNoBookingFoundTest() throws Exception{
		Mockito.when(flightBookingDao.deleteBooking(2008)).thenReturn(-1);
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_BOOKING_FOUND");
		flightBookingService.deleteBooking(2008);
	}
	
	@Test
	public void deleteBookingAmountAbove3000Test() throws Exception{
		Mockito.when(flightBookingDao.deleteBooking(2008)).thenReturn(0);
		ee.expect(Exception.class);
		ee.expectMessage("Service.AMOUNT_NOT_LESS_THAN_3000");
		flightBookingService.deleteBooking(2008);
	}
	
	@Test
	public void deleteBookingValidTest() throws Exception{
		Mockito.when(flightBookingDao.deleteBooking(2008)).thenReturn(2008);
		int value = flightBookingService.deleteBooking(2008);
		Assert.assertEquals(2008, value);
	}
	
	@Test
	public void updateBookingNoBookingFoundTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("NBF");
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_BOOKING_FOUND");
		flightBookingService.updateBooking(b.getBookingId(), b);
	}
	
	@Test
	public void updateBookingNoTicketsChangeTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("CNT");
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_TICKETS_CHANGE");
		flightBookingService.updateBooking(b.getBookingId(), b);
	}
	
	@Test
	public void updateBookingFlightFullTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("0");
		ee.expect(Exception.class);
		ee.expectMessage("Service.FLIGHT_FULL");
		flightBookingService.updateBooking(b.getBookingId(), b);
	}
	
	@Test
	public void updateBookingFlightAlmostFullTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("NSL1");
		ee.expect(Exception.class);
		ee.expectMessage("Sorry flight almost full... Only 1 left!!");
		flightBookingService.updateBooking(b.getBookingId(), b);
	}
	
	@Test
	public void updateBookingInsufficientAmountTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("IA200");
		ee.expect(Exception.class);
		ee.expectMessage("Insufficient Wallet Amount! Add more Rs. 200 to continue booking");
		flightBookingService.updateBooking(b.getBookingId(), b);
	}
	
	@Test
	public void updateBookingValidTest() throws Exception{
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		Mockito.when(flightBookingDao.updateBooking(b.getBookingId(), b)).thenReturn("2001");
		String value = flightBookingService.updateBooking(b.getBookingId(), b);
		Assert.assertEquals("2001", value);
	}
	
	@Test
	public void getAllBookingsInvalidTest() throws Exception{
		ArrayList<Booking> bookingList = new ArrayList<Booking>();
		Mockito.when(flightBookingDao.getAllBookings()).thenReturn(bookingList);
		ee.expect(Exception.class);
		ee.expectMessage("Service.NO_BOOKINGS_FOUND");
		flightBookingService.getAllBookings();
	}
	
	@Test
	public void getAllBookingsValidTest() throws Exception{
		ArrayList<Booking> bookingListFromDao = new ArrayList<Booking>();
		Booking b = new Booking();
		b.setCustomerId("S1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		b.setBookingCost(1200d);
		b.setBookingId(2001);
		bookingListFromDao.add(b);
		Mockito.when(flightBookingDao.getAllBookings()).thenReturn(bookingListFromDao);
		ArrayList<Booking> actualBookingList = flightBookingService.getAllBookings();
		Assert.assertEquals(1, actualBookingList.size());
	}
	
	
}
