package com.infy.dao;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.infy.model.Booking;
import com.infy.model.Flight;

@RunWith(SpringRunner.class)
@Rollback(true)
@Transactional
@SpringBootTest
public class FlightBookingDaoImplTest {
		
	@Autowired
	private FlightBookingDaoImpl flightBookingDao;
	
	@Test
	public void getAllFlightValidTest() throws Exception{
		ArrayList<Flight> flightList = flightBookingDao.getAllFlights();
		if(flightList.isEmpty()) {
			Assert.assertTrue(false);
		}
		else {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void bookFlightCancelledFlightTest(){
		Booking b = new Booking();
		b.setCustomerId("S1001");
		b.setFlightId("IND-103");
		b.setNoOfTickets(2);
		b.setPassengerName("Vinay");
		String value = flightBookingDao.bookFlight(b);
		Assert.assertEquals("FCIND-103", value);
	}
	
	@Test
	public void bookFlightInsuffucientAmountTest(){
		Booking b = new Booking();
		b.setCustomerId("S1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(9);
		b.setPassengerName("Vinay");
		String value = flightBookingDao.bookFlight(b);
		Assert.assertTrue(value.startsWith("IA"));
	}
	
	
	@Test
	public void bookFlightFlightAlmostFullTest(){
		Booking b = new Booking();
		b.setCustomerId("S1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(90);
		b.setPassengerName("Vinay");
		String value = flightBookingDao.bookFlight(b);
		Assert.assertTrue(value.startsWith("NSL"));
	}
	
	@Test
	public void bookFlightNoCustomerFoundTest(){
		Booking b = new Booking();
		b.setCustomerId("Q1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(1);
		b.setPassengerName("Vinay");
		String value = flightBookingDao.bookFlight(b);
		Assert.assertTrue(value.startsWith("NCF"));
	}
	
	@Test
	public void bookFlightValidTest(){
		Booking b = new Booking();
		b.setCustomerId("S1001");
		b.setFlightId("IND-101");
		b.setNoOfTickets(1);
		b.setPassengerName("Vinay");
		String value = flightBookingDao.bookFlight(b);
		Assert.assertNotNull(value);
	}
	
	@Test
	public void deleteBookingNoBookingFoundTest() {
		int value = flightBookingDao.deleteBooking(2010);
		Assert.assertEquals(-1, value);	
	}
	
	@Test
	public void deleteBookingCostGreterThan3000Test() {
		int value = flightBookingDao.deleteBooking(2006);
		Assert.assertEquals(0, value);	
	}
	
	@Test
	public void deleteBookingValidTest() {
		int value = flightBookingDao.deleteBooking(2001);
		Assert.assertNotNull(value);
	}
	
	@Test
	public void updateBookingNoBookingFoundTest() {
		Booking b = new Booking();
		b.setBookingId(2011);
		b.setNoOfTickets(2);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertTrue(value.startsWith("NBF"));
	}
	
	@Test
	public void updateBookingChangeTicketTest() {
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setNoOfTickets(3);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertTrue(value.startsWith("CNT"));
	}
	
	@Test
	public void updateBookingFlightAlmostFullTest() {
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setNoOfTickets(35);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertTrue(value.startsWith("NSL"));
	}
	
	
	@Test
	public void updateBookingInsufficientAmountTest() {
		Booking b = new Booking();
		b.setBookingId(2001);
		b.setNoOfTickets(4);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertTrue(value.startsWith("IA"));
	}
	
	@Test
	public void updateBookingExtraTicketValidTest() {
		Booking b = new Booking();
		b.setBookingId(2003);
		b.setNoOfTickets(3);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertEquals("2003", value);
	}
	
	@Test
	public void updateBookingLessTicketsValidTest() {
		Booking b = new Booking();
		b.setBookingId(2003);
		b.setNoOfTickets(1);
		String value = flightBookingDao.updateBooking(b.getBookingId(), b);
		Assert.assertEquals("2003", value);
	}
	
	@Test
	public void getAllBookingTest() {
		ArrayList<Booking> bookingList = flightBookingDao.getAllBookings();
		if(bookingList.isEmpty()) {
			Assert.assertTrue(false);
		}
		else {
			Assert.assertTrue(true);
		}
	}
	
}
