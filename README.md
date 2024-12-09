# SJSU Event Management System
## Project Overview
SJSU Event Management System implements a comprehensive event management system that allows users to manage events and handle tickets. The system features role-based access control, ensuring secure event management and ticket handling while having a user-friendly design. The chosen topic is Event Management System that allow users to signup and signin, manage events by create, modify, delete and search. Users also have the ability to purchase tickets and view them.
 
## Key Feature

Key features that were implemented in the project include CRUD operations that allow users to create, read, update and delete for events. Additionally, users will be able to search specific events using eventId. For authentication, users must enter correct login information which will be checked if there is a match in the database. 

## Getting Started
### Prerequisites
- JDK 17 or later
- MySQL 8.0 or later
- Gradle 7.x or later
- Spring Boot 3.x

### Dependencies

```bash
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    annotationProcessor 'org.projectlombok:lombok'
    
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```



## Database Setup
1. Create MySQL Database
```bash
CREATE DATABASE your_database_name;
```
2. Create Tables
```bash
-- Member Table
CREATE TABLE member (
    member_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, 
    role VARCHAR(50) NOT NULL,
    name VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);

-- Event Table
CREATE TABLE event (
    event_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_owner_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    max_audience INT,
    entrance_fee INT,
    show_date DATE,
    start_time VARCHAR(255),
    end_time VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (event_owner_id) REFERENCES member(member_id)
);

-- Ticket Table
CREATE TABLE ticket (
    ticket_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid VARCHAR(255) UNIQUE NOT NULL,
    due_date DATE,
    event_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES event(event_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);
```

## Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 8080

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```
## Building and Running
1. Clone the repository

```bash
git clone [repository-url]
```

2. Navigate to directory
```bash
cd [name of folder]
```

3. Build the project
```bash
./gradlew build
```
4. Run
```bash
./gradlew bootRun
```
Swagger UI documentation will be available at http://localhost:8080/swagger-ui.html


## License

[MIT](https://choosealicense.com/licenses/mit/)
