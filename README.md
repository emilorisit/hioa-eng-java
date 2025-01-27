# HiOA Engineering Java Project

This project is a legacy Java application developed in 2012, demonstrating foundational programming skills in GUI design, event-driven architecture, and client-server communication.

## Project Overview

### Features
- **Java Swing/AWT-based GUI**: Interactive graphical interface with modular components for login, history, and data visualization.
- **Event-Driven Architecture**: Implements custom events and listeners for dynamic interactions.
- **Client-Server Communication**: Uses TCP/IP protocols for networking, connecting the client application to a backend server.
- **Database Integration**: Supports MySQL via the `mysql-connector-java` library.

### Directory Structure
```
HiOA-Eng-Java-Project/
├── README.md               # Project documentation
├── .gitignore              # Git ignore rules
├── src/                    # Source code
│   ├── client/             # Client-side application
│   │   ├── gui/            # Graphical user interface components
│   │   ├── model/          # Application models and business logic
│   │   ├── network/        # Networking logic
│   └── server/             # (Optional) Server-side code, if provided
├── libs/                   # External libraries (e.g., MySQL connector)
│   └── mysql-connector-java-5.1.22.jar
└── resources/              # Additional assets (e.g., configurations, icons)
```

## Requirements
- Java SE 7 or newer
- MySQL database (if the application interacts with a database)

## Setup and Usage

### 1. Compile the Code
Navigate to the `src` directory and compile using a Java compiler:
```bash
javac -cp libs/mysql-connector-java-5.1.22.jar -d bin src/**/*.java
```

### 2. Run the Application
Run the `ClientApp` class (from `client.model`) as the entry point:
```bash
java -cp bin:libs/mysql-connector-java-5.1.22.jar client.model.ClientApp
```

### 3. Configure the Database
If using MySQL:
1. Set up the database schema as expected by the application (details may be hardcoded in the source).
2. Update connection settings in the source code (e.g., hostname, username, password).

## Modernization Ideas
Given the age of the project, here are suggestions for improvement:
- **Upgrade GUI**: Replace Swing/AWT with JavaFX for a modern look and better maintainability.
- **Adopt Build Tools**: Use Maven or Gradle for dependency management and builds.
- **Enhance Testing**: Integrate unit tests using JUnit or similar frameworks.
- **Improve Security**: Replace hardcoded credentials with environment variables or configuration files.


