# Hepta Fit - Complete Wellness Application

Hepta Fit is a comprehensive wellness application designed to help users reach their health, fitness, and mental well-being goals. The application integrates various aspects of wellness including nutrition, fitness tracking, mental health, and sleep monitoring into a single, user-friendly platform.

## 🌟 Features

### 1. Fitness Tracker
- Create and manage workout schedules
- Track exercise repetitions, sets, and weights
- Monitor progress towards fitness goals
- Special section for HIIT and cardio workouts

### 2. Nutrition Planner
- Personalized macro-nutrient calculator
- Daily calorie needs assessment
- Food categorization and nutritional information
- Healthy meal suggestions and alternatives

### 3. Mental Wellness Chatbot
- AI-powered conversation partner
- Mood and stress level tracking
- Daily affirmations and motivational tips
- Mindfulness and meditation guidance

### 4. Sleep Monitor
- Track sleep patterns and quality
- Sleep cycle analysis (REM, deep sleep, light sleep)
- Personalized sleep improvement suggestions
- Environmental factor tracking

## 🛠 Technology Stack

- **Backend**: Java 17 with Spring Boot 3.2.3
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Database**: H2 (for development)
- **Build Tool**: Maven

## 📋 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/heptafit.git
   cd heptafit
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Open your browser and navigate to `http://localhost:8080`
   - H2 Console is available at `http://localhost:8080/h2-console`

## 🏗 Project Structure

```
heptafit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/heptafit/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── HeptaFitApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🔒 Security Note

This application includes sensitive user data. In a production environment, ensure to:
- Use proper authentication and authorization
- Implement secure password storage
- Enable HTTPS
- Protect API endpoints
- Follow GDPR and other relevant data protection regulations

## 🤝 Contributing

We welcome contributions! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- Heptafit

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the responsive UI components
- The open-source community for inspiration and tools 
