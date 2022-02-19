BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	"username"	varchar(255) NOT NULL UNIQUE,
	"password"	varchar(255) NOT NULL,
	"email"	varchar(255) NOT NULL,
	"firstName"	varchar(255) NOT NULL,
	"lastName"	varchar(255) NOT NULL,
	"gender"	char(1) NOT NULL,
	"personID"	varchar(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS "person" (
	"personID"	varchar(255) NOT NULL UNIQUE,
	"associatedUsername"	varchar(255) NOT NULL,
	"firstName"	varchar(255) NOT NULL,
	"lastName"	varchar(255) NOT NULL,
	"gender"	char(1) NOT NULL,
	"fatherID"	varchar(255),
	"motherID"	varchar(255),
	"spouseID"	varchar(255)
);
CREATE TABLE IF NOT EXISTS "event" (
	"eventID"	varchar(255) NOT NULL UNIQUE,
	"associatedUsername"	varchar(255) NOT NULL,
	"personID"	varchar(255) NOT NULL,
	"latitude"	float NOT NULL,
	"longitude"	float NOT NULL,
	"country"	varchar(255) NOT NULL,
	"city"	varchar(255) NOT NULL,
	"eventType"	varchar(255) NOT NULL,
	"year"	integer NOT NULL
);
CREATE TABLE IF NOT EXISTS "authtoken" (
	"authtoken"	varchar(255) NOT NULL UNIQUE,
	"username"	varchar(255) NOT NULL
);
COMMIT;
