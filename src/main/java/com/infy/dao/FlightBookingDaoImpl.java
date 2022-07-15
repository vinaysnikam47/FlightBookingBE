package com.infy.dao;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.infy.entity.BookingEntity;
import com.infy.entity.CustomerEntity;
import com.infy.entity.FlightEntity;
import com.infy.model.Booking;
import com.infy.model.Flight;
import com.infy.model.Status;

@Repository(value = "FlightBookingDao")
public class FlightBookingDaoImpl implements FlightBookingDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Flight> getAllFlights() {
		ArrayList<FlightEntity> flightEntityList = new ArrayList<FlightEntity>();
		String query = "Select f from FlightEntity f";
		Query q = entityManager.createQuery(query);
		flightEntityList = (ArrayList<FlightEntity>) q.getResultList();
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		if(flightEntityList.isEmpty()) {
			return flightList;
		}
		else {
			for(FlightEntity fe:flightEntityList) {
				Flight f = new Flight();
				f.setFlightId(fe.getFlightId());
				f.setAircraftName(fe.getAircraftName());
				f.setAvailableSeats(fe.getAvailableSeats());
				f.setFare(fe.getFare());
				ArrayList<Booking> bookingList = new ArrayList<Booking>();
				for(BookingEntity be: fe.getBookings()) {
					Booking b = new Booking();
					b.setBookingId(be.getBookingId());
					b.setBookingCost(be.getBookingCost());
					b.setCustomerId(be.getCustomerId());
					b.setNoOfTickets(be.getNoOfTickets());
					b.setPassengerName(be.getPassengerName());
					bookingList.add(b);
				}
				f.setBookings(bookingList);
				f.setStatus(fe.getStatus());
				flightList.add(f);
			}
		}
		return flightList;
	}

	@Override
	public String bookFlight(Booking bookingData) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, bookingData.getCustomerId());
		if(ce != null) {
			FlightEntity fe = entityManager.find(FlightEntity.class, bookingData.getFlightId());
			if(fe.getStatus() == Status.Cancelled) {
				return "FC" + fe.getFlightId();
			}
			else {
				if(fe.getAvailableSeats() >= bookingData.getNoOfTickets()) {
					Double bookingCost = bookingData.getNoOfTickets() * fe.getFare();
					if(bookingCost > ce.getWalletAmount()) {
						return "IA" + (bookingCost-ce.getWalletAmount());
					}
					else {
						BookingEntity be = new BookingEntity();
						be.setBookingCost(bookingCost);
						be.setCustomerId(bookingData.getCustomerId());
						be.setNoOfTickets(bookingData.getNoOfTickets());
						be.setPassengerName(bookingData.getPassengerName());
						be.setFlightId(bookingData.getFlightId());
						fe.setAvailableSeats(fe.getAvailableSeats()-bookingData.getNoOfTickets());
						fe.getBookings().add(be);
						ce.setWalletAmount(ce.getWalletAmount()-bookingCost);
						entityManager.persist(be);
						return be.getBookingId().toString();
					}
				}
				else {
					if(fe.getAvailableSeats() == 0) {
						return "0";
					}
					else {
						return "NSL" + fe.getAvailableSeats();
					}
				}
			}
			
		}
		else {
			return "NCF";
		}
	}

	@Override
	public Integer deleteBooking(Integer bookingId) {
		BookingEntity be = entityManager.find(BookingEntity.class, bookingId);
		if(be != null) {
			if(be.getBookingCost() <= 3000) {
				String flightId = be.getFlightId();
				String customerId = be.getCustomerId();
				FlightEntity fe = entityManager.find(FlightEntity.class, flightId);
				CustomerEntity ce = entityManager.find(CustomerEntity.class, customerId);
				
				Integer index = null; 
				for(BookingEntity b: fe.getBookings()) {
					if(b.getBookingId() == bookingId) {
						index = fe.getBookings().indexOf(b);
						break;
					}
				}
				
				fe.getBookings().remove(index.intValue());
				fe.setAvailableSeats(fe.getAvailableSeats() + be.getNoOfTickets());
				ce.setWalletAmount(ce.getWalletAmount()+ be.getBookingCost());
				entityManager.remove(be);
				return bookingId;
			}
			else {
				return 0;
			}
				
		}
		else {
			return -1;
		}
	}

	@Override
	public String updateBooking(Integer bookingId, Booking bookingData) {
		BookingEntity be = entityManager.find(BookingEntity.class, bookingId);
		if(be != null) {
			FlightEntity fe = entityManager.find(FlightEntity.class, be.getFlightId());
			CustomerEntity ce = entityManager.find(CustomerEntity.class, be.getCustomerId());
			if(bookingData.getNoOfTickets() > be.getNoOfTickets()) {
				Integer extratickets = bookingData.getNoOfTickets() - be.getNoOfTickets();
				if(extratickets > fe.getAvailableSeats()) {
					if(fe.getAvailableSeats() == 0) {
						return "0";
					}
					else {
						return "NSL" + fe.getAvailableSeats();
					}
				}
				else {
					Double extraTicketbookingCost = extratickets*fe.getFare();
					if(extraTicketbookingCost <= ce.getWalletAmount()) {
						be.setNoOfTickets(bookingData.getNoOfTickets());
						be.setBookingCost(be.getBookingCost() + extraTicketbookingCost);
						ce.setWalletAmount(ce.getWalletAmount()-extraTicketbookingCost);
						fe.setAvailableSeats(fe.getAvailableSeats()-extratickets);
						return bookingId.toString();
					}
					else {
						return "IA" + (extraTicketbookingCost - ce.getWalletAmount());
					}
				}
			}
			else if(bookingData.getNoOfTickets() < be.getNoOfTickets()) {
				Integer lessTickets = be.getNoOfTickets() - bookingData.getNoOfTickets();
				Double lessTicketCost = lessTickets*fe.getFare();
				be.setBookingCost(be.getBookingCost() - lessTicketCost);
				be.setNoOfTickets(bookingData.getNoOfTickets());
				ce.setWalletAmount(ce.getWalletAmount() + lessTicketCost);
				fe.setAvailableSeats(fe.getAvailableSeats() + lessTickets);
				return bookingId.toString();
			}
			else {
				return "CNT";
			}
		}
		else {
			return "NBF";
		}
	}

	@Override
	public ArrayList<Booking> getAllBookings() {
		ArrayList<Booking> bookingsList = new ArrayList<Booking>();
		String query = "select b from BookingEntity b";
		Query q = entityManager.createQuery(query);
		@SuppressWarnings("unchecked")
		ArrayList<BookingEntity> bookingEntityList = (ArrayList<BookingEntity>) q.getResultList();
		if(bookingEntityList.isEmpty()) {
			return bookingsList;
		}
		else {
			for(BookingEntity be: bookingEntityList) {
				Booking b = new Booking();
				b.setBookingCost(be.getBookingCost());
				b.setBookingId(be.getBookingId());
				b.setCustomerId(be.getCustomerId());
				b.setFlightId(be.getFlightId());
				b.setNoOfTickets(be.getNoOfTickets());
				b.setPassengerName(be.getPassengerName());
				bookingsList.add(b);
			}
		}
		return bookingsList;
	}

}
