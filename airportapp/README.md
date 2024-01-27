Airport Application
======

Prerequisites
----

Install necessary software:

* Java 21
* PostgreSQL (if you plan to use a local database) with configured **postgres** read/write user

## How to start locally via maven

### Local Database Setup:
1. Connect to a local **PostgreSQL** database and make sure it runs on port `5432`
2. Create a user named `postgres` with full privileges and password of choice
3. Create a database with name `airportdb` (public schema, owner `postgres`, accessible by user `postgres`)


### Run configuration:
1. Check the yaml file for default values before overriding env var.
2. Open your AirportApp run configuration.
3. In the environment variables input field add the following env var:
```bash
DB_USERNAME=<DB_USERNAME>
DB_PASSWORD=<DB_PASSWORD>
DB_URL=<DB_URL>

```
### Airport and Flights files location
1. Check the file location for the airport and flight csv files
2. Open the application.yml file
3. Change the data of the brackets to your files' locations(OR reproduce the same steps as the 
configuration by adding environment variables with their locations)
4. 
### Applications start:
**Congratulations** now you can successfully run AirportApp locally!