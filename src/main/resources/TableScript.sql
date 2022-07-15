drop database if exists flight_booking;

create database flight_booking default character set latin1
collate latin1_general_cs;

use flight_booking;

create table Customer(
customer_id varchar(5) not null,
customer_name varchar(20) not null,
wallet_amount float(12, 2) not null,
customer_Type varchar(10) not null,
constraint customer_id_PK primary key (customer_id)
);

select * from Customer;

insert into CUSTOMER values('P1001', 'Tom', 0, 'Platinum');
insert into CUSTOMER values('G1001', 'John', 2000, 'Gold');
insert into CUSTOMER values('S1001', 'Steve', 4500, 'Silver');

create table Flight(
flight_id varchar(7) not null,
aircraft_name varchar(20) not null,
fare int(5) not null,
available_seats int(4) not null,
status varchar(10) not null,
constraint flight_id_PK primary key (flight_id)
);

select * from Flight;

insert into FLIGHT values('IND-101', 'Delta Airlines', 600, 10, 'Running');
insert into FLIGHT values('IND-102', 'Jet Blue', 750, 20, 'Running');
insert into FLIGHT values('IND-103', 'United Airlines', 800, 10, 'Cancelled');

create table Booking(
booking_id int(10) not null auto_increment,
customer_id varchar(5) not null,
flight_id varchar(7) not null,
passenger_name varchar(50) not null,
no_of_tickets int(10) not null,
booking_cost int(10) not null,
constraint booking_id_PK primary key (booking_id),
constraint customer_id_FK foreign key (customer_id) references Customer(customer_id),
constraint flight_id_FK foreign key (flight_id) references Flight(flight_id)
);

select * from Booking;

insert into Booking values(2001, 'P1001', 'IND-101', 'John Doe', 3, 1800);
insert into Booking values(2002, 'P1001', 'IND-102', 'Nicholas', 3, 2250);
insert into Booking values(2003, 'S1001', 'IND-101', 'Alan Paul', 2, 1200);
insert into Booking values(2004, 'G1001', 'IND-102', 'Joe Richardson', 2, 1500);
insert into Booking values(2005, 'S1001', 'IND-103', 'Steve Parker', 1, 800);
insert into Booking values(2006, 'G1001', 'IND-103', 'David Milley', 4, 3200);

commit;