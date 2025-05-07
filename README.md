# RecipEase

RecipEase is a web-based social network built with Spring Boot, designed to help novice cooks create, share, and discover recipes. 
It features a dynamic recipe system that automatically integrates a prepopulated glossary of cooking terms and ingredient substitutions. 
The platform ensures secure user authentication with profile management and implements role-based access control for content moderation. 
To enhance scalability and streamline deployment, both the application and its PostgreSQL database are containerized using Docker.

This project is part of my Master's thesis in Software Engineering at The University of Scranton.

## Technologies Used

- **Backend**: Spring Boot (Java)
- **Frontend**: HTML (Thymeleaf), JavaScript, and CSS
- **Database**: PostgreSQL
- **Build & Dependency Management Tool**: Maven
- **Containerization**: Docker (Compose, database setup via .sh script)

## Installation & Setup

1. Clone the repository:
   ```bash
   git clone git clone reese-david-recipease.bundle
   ```

2. Navigate to the project directory:
   ```bash
   cd reese-david-recipease
   ```

3. Note for Windows users: You may need to change the line endings of the db-init.sh script from CRLF (Windows-style) to LF (Unix-style) for the database to initialize correctly.
    To do this in IntelliJ (or any other editor), open the file, click on CRLF in the bottom-right corner, and select LF.
    Then save the file. 

4. Build and run the application using Docker:
   ```bash
   docker compose up --build
   ```

5. Access the application at:
   ```
   http://localhost:8080
   ```