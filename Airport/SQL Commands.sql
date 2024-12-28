DROP DATABASE IF EXISTS Icarus; 
CREATE DATABASE Icarus;
USE Icarus;

DROP TABLE IF EXISTS `Flights`;
DROP TABLE IF EXISTS `Users`;
DROP TABLE IF EXISTS `Airports`;
DROP TABLE IF EXISTS `Aircrafts`;
DROP TABLE IF EXISTS `Company`;
DROP TABLE IF EXISTS `Ticket`;
DROP TABLE IF EXISTS `WaitingList`;

-- Handle Admins, Customers, Representatives
CREATE TABLE Users ( 
	Username VARCHAR(255) PRIMARY KEY, 
	Password VARCHAR(255),
	Role ENUM("Admin", "Representative", "Customer"),
	Fname VARCHAR(255), 
	Lname VARCHAR(255)
	); 

-- Table for Company
CREATE TABLE AirlineCompany ( 
    CompanyID INT PRIMARY KEY,
    CompanyName VARCHAR(255),
    CompanyCode CHAR(2) NOT NULL
	);
CREATE INDEX idx_airlinecompany_companyname ON AirlineCompany(CompanyName);

-- Table for Airports
CREATE TABLE Airports ( 
	PortID CHAR(3) PRIMARY KEY,
	AirportName VARCHAR(255)
	); 

-- Table for Aircrafts
CREATE TABLE Aircrafts ( 
    AircraftID INT PRIMARY KEY,
    CompanyID INT,
    FOREIGN KEY (CompanyID) REFERENCES AirlineCompany(CompanyID)
	);

-- Table for Flights
CREATE TABLE Flights ( 
	FlightNumber INT PRIMARY KEY, 
	OperationDays ENUM("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"), 
	Seats INT, 
	GlobalType ENUM("Domestic", "International"),
	TripType ENUM("One-Way", "Round Trip"),
	DepartLocation CHAR(3), 
	DepartTime DATETIME, 
	ArrivalLocation CHAR(3),
	ArrivalTime DATETIME,
    AirlineCom VARCHAR(255), 
	Aircraft INT,
	FOREIGN KEY (DepartLocation) REFERENCES Airports(PortID),
	FOREIGN KEY (ArrivalLocation) REFERENCES Airports(PortID),
	CONSTRAINT fk_airline_com FOREIGN KEY (AirlineCom) REFERENCES AirlineCompany(CompanyName),
	FOREIGN KEY (Aircraft) REFERENCES Aircrafts(AircraftID)
	);

-- Table for Waiting List
CREATE TABLE WaitingList ( 
	ListID INT PRIMARY KEY, 
	Waiting VARCHAR(255),
	FOREIGN KEY (ListID) REFERENCES Flights(FlightNumber),
	FOREIGN KEY (Waiting) REFERENCES Users(username)
	); 
	
-- Table for Tickets
CREATE TABLE Ticket ( 
	BuyerID VARCHAR(255),
	TicketID INT UNIQUE,
	FromAirport CHAR(3),
	ToAirport CHAR(3), 
	FlightNum INT, 
	DepartureDate DATE,
	DepartureTime TIME,
	SeatNum INT,
	ClassType ENUM("ECONOMY","BUSINESS", "FIRST"),
	TotalFare FLOAT, 
	BookingFee FLOAT,
	PurchaseDate DATE, 
	PurchaseTime TIME,
	-- PRIMARY KEY (BuyerID, TicketID),
	PRIMARY KEY (TicketID),
	FOREIGN KEY (BuyerID) REFERENCES Users(username),
	FOREIGN KEY (FromAirport) REFERENCES Airports(PortID),
	FOREIGN KEY (ToAirport) REFERENCES Airports(PortID),
	FOREIGN KEY (FlightNum) REFERENCES Flights(FlightNumber)
	);
	
-- CREATE TABLE SEQUENCE

-- Table for Reservation Portfolio 
CREATE TABLE Portfolio (
	Holder VARCHAR(255) PRIMARY KEY,
    Ticket INT,
    FOREIGN KEY (Holder) REFERENCES Users(username),
    FOREIGN KEY (Ticket) REFERENCES Ticket(TicketID)
    );


-- 
-- Start of data
-- 

INSERT INTO Users (Username, Password, Role, Fname, Lname) VALUES
("A","1","Admin","Speed","Gain"),
("Administrator", "pass", "Admin", "Local", "Host"), 
("Rep", "pass", "Representative", "Echo", "Off"), 
("FirstUser", "pass", "Customer", "John", "Doe"); 

INSERT INTO AirlineCompany (CompanyID, CompanyName, CompanyCode) VALUES
(1, "Lethal Company", "LC"),
(2, "Harmless Company", "HC"),
(3, "Neutral Company", "NC");

INSERT INTO Airports (PortID) VALUES 
("AAA"),
("AAB"),
("AAC"),
("AAD"),
("AAE"),
("AAF"),
("AAG"),
("AAH"),
("AAI"),
("AAJ"),
("AAK"),
("AAL"),
("AAM"),
("AAN"),
("AAO"),
("AAP"),
("AAQ"),
("AAR"),
("AAS"),
("AAT"),
("AAU"),
("AAV"),
("AAW"),
("AAX"),
("AAY"),
("AAZ"),
("ABA"),
("ABB"),
("ABC"),
("ABD"),
("ABE"),
("ABF"),
("ABG"),
("ABH"),
("ABI"),
("ABJ"),
("ABK"),
("ABL"),
("ABM"),
("ABN"),
("ABO"),
("ABP"),
("ABQ"),
("ABR"),
("ABS"),
("ABT"),
("ABU"),
("ABV"),
("ABW"),
("ABX"),
("ABY"),
("ABZ"),
("ACA"),
("ACB"),
("ACC"),
("ACD"),
("ACE"),
("ACF"),
("ACG"),
("ACH");

INSERT INTO Aircrafts (AircraftID, CompanyID) VALUES 
(111, 1),
(222, 2),
(333, 3);

INSERT INTO Flights() VALUES 
(1, "Monday", 1, 	"Domestic", "One-Way", "AAA", '1337-01-01 00:01:00', "AAB", '1337-01-02 00:02:00', "Lethal Company", 111),
(2, "Tuesday", 2, 	"Domestic", "Round Trip", "AAC", '1337-01-03 00:01:00', "AAD", '1337-01-04 00:02:00', "Lethal Company", 111),
(3, "Wednesday", 3, "International", "One-Way", "AAE", '1337-01-05 00:01:00', "AAF", '1337-01-06 00:02:00', "Harmless Company", 222),
(4, "Thursday", 4, 	"International", "Round Trip", "AAG", '1337-01-07 00:01:00', "AAH", '1337-01-08 00:02:00', "Harmless Company", 222),
(5, "Friday", 5, 	"Domestic", "One-Way", "AAI", '1337-01-09 00:01:00', "AAJ", '1337-01-10 00:02:00', "Neutral Company", 333),
(6, "Saturday", 6, 	"Domestic", "One-Way", "AAK", '1337-01-11 00:01:00', "AAL", '1337-01-12 00:02:00', "Neutral Company", 333),
(7, "Sunday", 7, 	"Domestic", "One-Way", "AAM", '1337-01-13 00:01:00', "AAN", '1337-01-14 00:02:00', "Lethal Company", 111),
(8, "Sunday", 3, 	"Domestic", "One-Way", "AAB", '1337-01-13 00:01:00', "ABC", '1337-01-14 00:02:00', "Neutral Company", 333);

INSERT INTO Ticket (TicketID, FromAirport, ToAirport, Flightnum, DepartureDate, DepartureTime, SeatNum, ClassType, TotalFare, BookingFee) VALUES 
(1, "AAA", "AAB", 1, '2023-01-01', '00:03:00', 5, "ECONOMY", 5.00, 3.00),
(2, "AAC", "AAD", 2, '2023-01-02', '00:03:00', 5, "BUSINESS", 7.00, 3.00),
(3, "AAE", "AAF", 3, '2023-01-03', '00:03:00', 1, "FIRST", 10.00, 3.00),
(4, "AAG", "AAH", 4, '2023-01-04', '00:00:00', 5, "ECONOMY", 5.00, 3.00);