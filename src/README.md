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
   git clone https://github.com/davidreese3/RecipEase.git
   ```

2. Navigate to the project directory:
   ```bash
   cd RecipEase
   ```

3. Build and run the application using Docker:
   ```bash
   docker compose up --build
   ```

4. Access the application at:
   ```
   http://localhost:8080
   ```