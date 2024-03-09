## ------------------------------------------------------------------- ##

#### API Documentation with Swagger OpenAPI

The IoTconfig application utilizes Swagger OpenAPI for API documentation. With Swagger OpenAPI, you can explore the
endpoints and request/response schemas.

You can access Swagger UI at the following endpoint:

http://localhost:8080/swagger-ui.html

## ------------------------------------------------------------------- ##

#### Functional Specifications

The IoTconfig Application is a follow-user-stories-MVP implementing the following stories:

Create Configuration

User Story: As a user, I would like to have the ability to create a configuration.
Input: Device identifier (text), configuration (text, JSON)
Output: All fields of the configuration domain class

Read Configuration

User Story: As a user, I would like to have the ability to read a configuration.
Input: Configuration database identifier
Output: Only the "configuration" field and "creation date" (yyyy-mm-dd)

Read All Configurations

User Story: As a user, I would like to have the ability to read all configuration.
Input: None
Output: All configurations each containing only the "configuration" field and "creation date" (yyyy-mm-dd)

Update Configuration

User Story: As a user, I would like to have the ability to update a configuration.
Input: Not specified
Output: All fields of the configuration domain class

Delete Configuration

User Story: As a user, I would like to have the ability to delete a configuration.
Input: Configuration database identifier
Output: None (indicates the configuration is deleted)

## ------------------------------------------------------------------- ##

#### Application Design

1. The IoTconfig application adopts a specific design approach to address best practices and avoid potential issues
   related to storing zoned datetime in MySQL. Specifically, the application stores and retrieves LocalDateTime and
   ZoneOffset in separate columns within the database.

   Rationale
   Best Practices: Storing datetime values in separate columns aligns with database design best practices, facilitating
   easier querying and manipulation of data.

   Avoiding Issues: MySQL has limitations when handling zoned datetime values, which can lead to unexpected behavior or
   errors. Separating LocalDateTime and ZoneOffset into distinct columns helps mitigate these potential issues.

2. Initial Data Loading: Two Devices and Configurations

   The application initializes the database with two predefined devices and their corresponding configurations. This
   setup ensures that the application has foundational data to work with upon deployment.

3. Adding Data

   When adding a new configuration, the application mandates that it be linked to a device
   already present in the database. This requirement ensures that configurations are logically connected to specific
   devices, maintaining data coherence and integrity.

4. FE -> BE Contract

   When adding a new configuration, the backend expects the following payload:

   {
   "deviceIdentifier": "<device_identifier>",
   "configuration_json_string": "{\"key\":\"value\"}"
   }

   When modifying an existing configuration, the backend expects the following payload:

   {
   "id": <configuration_id>,
   "deviceIdentifier": "<device_identifier>",
   "configuration_json_string": "{\"key\":\"value\"}"
   }

## ------------------------------------------------------------------- ##

#### Technology stack

Spring Boot: Version 3.2.3
Java: Version 21
Apache Maven 3.9.6

Here's the list of dependencies along with their versions:

Spring Boot Starter Web: Version 2.6.4
Spring Boot Starter Data JPA: Version 2.6.4
MySQL Connector/J: Version 8.0.33
Spring Boot Starter Test: Version 2.6.4
Springdoc OpenAPI Starter Webmvc UI: Version 2.0.2
Springdoc OpenAPI Common: Version 1.7.0
Project Lombok: Version RELEASE
H2 Database: Version - NA
Jackson Datatype JSR310: Version 2.16.1
Spring Boot Starter Validation: Version 3.1.0

## ------------------------------------------------------------------- ##

#### Running the Application with Docker

This guide assumes that Docker is installed and running on your machine. If Docker is not installed, please visit
the [official Docker website](https://www.docker.com/get-started) for installation instructions.

## Steps to Run the Application

1. **Clone the Repository**

   First, clone the repository to your local machine using Git:

   git clone <repository-url>

   Replace `<repository-url>` with the actual URL of the Git repository.

2. Go to the the root directory of the project where the `docker-compose.yml` file is located and execute these commands
   to

   -- build the application JAR file
   -- build the Docker image of the application
   -- build the Docker image of the database
   -- run both as Docker containers

   execute the following commands:

   mvn clean install
   docker-compose up --build

   You should see logs in the console indicating that the application and the mysql service are up and running.

## Accessing the Application

Once the application is running, you can access it by navigating to `http://localhost:8080` in your web browser.

## Stopping the Application

To stop and remove the containers created by Docker Compose, use the following command:

docker-compose down

This command stops all running services and removes the containers to clean up.


