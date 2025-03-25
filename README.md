## Book Management System using Spring Boot
The Book Management System, built with Spring Boot, facilitates an efficient book inventory with attributes including title, genre (limited to Fiction, Thriller, Mystery, Poetry, Horror, and Satire), ISBN code, author details, and year of publication.
Users can seamlessly search for books based on title, author, year of publication, or genre. The system incorporates a shopping cart for users to add and view selected books.
Checkout options include Web, USSD, and Transfer payment methods, with a simulated payment process. Additionally, users can conveniently access their purchase history.
This concise and powerful system ensures a user-friendly experience in managing and exploring a diverse book collection.

Before getting started, ensure you have the following components installed:

1. **This project was built using JDK 17, you would need JDK 17 installed on you local machine.**

- [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)


### Installation

1. **Clone the Repository:**

   ```bash
   git https://github.com/Greatnex1/book-buddy
   cd book-store
   ```

2. **Configure the Database:**

   A default database configuration exist using postgresql connection.
   if there is need to change, modify the `src/main/resources/application.properties` file to include your database connection details.

   ```
   ## Build and Run the Application:

Execute the following command to build and run the application:

````bash
mvn spring-boot:run
````
## Table of Contents
- [Getting Started](#getting-started)
   - [Prerequisites Requirement](#prerequisites-requirement)
   - [Installation](#installation)
- [Technologies Used](#technologies-used)

## Technologies Used

The Book Management System leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The fundamental programming language employed for crafting application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Data JPA**: Provides data access and manipulation capabilities using the Java Persistence API.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **Postgresql**: A widely-used in-memory database management system.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Git**: Version control for collaborative development.
- **Docker**: Containerization lets you build, test, and deploy applications quickly.
- **AmazonS3**: A remote storage client where books were being uploaded to and saved.
