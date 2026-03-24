# 🤖 AI Chat Assistant
### Full-Stack AI Application with Spring Boot & React

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/React-18.2-blue?style=for-the-badge&logo=react" alt="React">
  <img src="https://img.shields.io/badge/Google-Gemini%20API-blue?style=for-the-badge&logo=google" alt="Google Gemini">
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java" alt="Java">
  <!-- CI/CD Status Badges -->
  <a href="https://github.com/your-username/SpringAIDemo/actions/workflows/ci.yml">
    <img src="https://github.com/your-username/SpringAIDemo/actions/workflows/ci.yml/badge.svg" alt="CI Pipeline">
  </a>
  <a href="https://github.com/your-username/SpringAIDemo/actions/workflows/cd.yml">
    <img src="https://github.com/your-username/SpringAIDemo/actions/workflows/cd.yml/badge.svg" alt="CD Pipeline">
  </a>
</p>

---

## 📌 Project Overview

A **production-ready, full-stack AI Chat Assistant** that leverages the power of **Google Gemini API** through **Spring AI** to provide intelligent conversational responses. This project demonstrates enterprise-grade software development practices, including clean architecture, proper error handling, logging, and a modern responsive UI.

### 🎯 Key Highlights

- ✅ **Real-time AI conversations** with Google Gemini API
- ✅ **Session-based chat history** for contextual responses
- ✅ **RESTful API** with proper HTTP methods
- ✅ **Clean Architecture** (Controller → Service → DTO)
- ✅ **Production-ready error handling** with global exception handler
- ✅ **Comprehensive logging** with SLF4J
- ✅ **Modern React UI** with typing indicators and loading states
- ✅ **CORS enabled** for frontend-backend integration

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Java 17, Spring Boot 3.4.1, Spring AI |
| **Frontend** | React 18, Vite, Axios |
| **AI/ML** | Google Gemini API |
| **Build Tools** | Maven (Backend), npm (Frontend) |
| **API** | RESTful API, JSON |

---

## 📂 Project Structure

```
SpringAIDemo/
├── SpringAIDemo/                    # Backend (Spring Boot)
│   ├── src/main/java/com/Ai/SpringAIDemo/
│   │   ├── config/                   # Configuration classes
│   │   │   ├── CorsConfig.java       # CORS configuration
│   │   │   └── GeminiChatModelConfig.java  # Gemini configuration
│   │   ├── controller/               # REST Controllers
│   │   │   └── ChatController.java   # Chat API endpoints
│   │   ├── dto/                      # Data Transfer Objects
│   │   │   ├── ChatRequest.java      # Request payload
│   │   │   ├── ChatResponse.java     # Response payload
│   │   │   └── ErrorResponse.java    # Error response
│   │   ├── exception/                # Exception handling
│   │   │   ├── AIChatException.java  # Custom exception
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── service/                  # Business logic
│   │   │   ├── AIService.java        # AI service integration
│   │   │   └── ChatService.java      # Chat service
│   │   └── SpringAiDemoApplication.java
│   ├── src/main/resources/
│   │   └── application.properties    # App configuration
│   └── pom.xml                       # Maven dependencies
│
└── frontend/                         # Frontend (React)
    ├── src/
    │   ├── App.jsx                   # Main component
    │   ├── index.css                 # Styling
    │   └── main.jsx                  # Entry point
    ├── package.json                  # npm dependencies
    └── vite.config.js               # Vite configuration
```

---

## ✨ Features

### Backend Features
- **AI-Powered Conversations**: Integration with Google Gemini API via Spring AI
- **Contextual Chat History**: Maintains conversation history per user session (up to 10 message pairs)
- **RESTful API Design**: Proper endpoints with GET and POST methods
- **Input Validation**: Request validation using Jakarta Validation
- **Global Exception Handling**: Centralized error handling with proper HTTP status codes
- **Structured Logging**: SLF4J logging for debugging and monitoring
- **CORS Support**: Cross-origin resource sharing enabled for frontend integration
- **Health Check Endpoint**: `/api/health` for service monitoring

### Frontend Features
- **Modern Chat UI**: Clean, responsive interface with Material Design inspired styling
- **Real-time Messaging**: Instant responses with loading indicators
- **Typing Animation**: Visual feedback while AI processes requests
- **Session Management**: Unique user ID generation for chat history tracking
- **Error Handling**: User-friendly error messages with visual indicators
- **Auto-scroll**: Automatically scrolls to latest message
- **Clear Chat**: Option to clear conversation history

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17** or higher
- **Node.js 18** or higher
- **Maven 3.8** or higher
- **Google Gemini API Key** (get one at https://aistudio.google.com/app/apikey)

---

### Backend Setup

1. **Navigate to the backend directory:**
   ```bash
   cd SpringAIDemo
   ```

2. **Configure your Google Gemini API Key:**
   
   Open [`application.properties`](SpringAIDemo/src/main/resources/application.properties) and replace the API key:
   ```properties
   spring.ai.gemini.api-key=YOUR_GEMINI_API_KEY_HERE
   ```

3. **Build and run the Spring Boot application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or build the JAR first:
   ```bash
   ./mvnw clean package
   java -jar target/SpringAIDemo-0.0.1-SNAPSHOT.jar
   ```

4. **Verify the backend is running:**
   - API Base URL: `http://localhost:8080`
   - Health check: `http://localhost:8080/api/health`

---

### Frontend Setup

1. **Navigate to the frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm run dev
   ```

4. **Access the application:**
   Open `http://localhost:5173` in your browser

---

## 📡 API Endpoints

### 1. Ask Question (GET)
Simple endpoint for quick questions.

```http
GET http://localhost:8080/api/ask?question=Hello&userId=user123
```

**Response:**
```json
{
  "answer": "Hello! How can I help you today?",
  "timestamp": "2026-03-17T10:30:00",
  "userId": "user123"
}
```

---

### 2. Send Chat Message (POST)
Full-featured chat endpoint with structured request.

```http
POST http://localhost:8080/api/chat
Content-Type: application/json

{
  "question": "Explain what is Spring Boot in simple terms",
  "userId": "user123"
}
```

**Response:**
```json
{
  "answer": "Spring Boot is a framework that makes it easy to create standalone Spring applications...",
  "timestamp": "2026-03-17T10:30:00",
  "userId": "user123"
}
```

---

### 3. Get Chat History
Retrieve conversation history for a user.

```http
GET http://localhost:8080/api/history?userId=user123
```

---

### 4. Clear Chat History
Clear all messages for a specific user.

```http
DELETE http://localhost:8080/api/history?userId=user123
```

---

### 5. Health Check
Check if the service is running.

```http
GET http://localhost:8080/api/health
```

**Response:**
```json
"AI Chat Service is running"
```

---

## 📸 Screenshots

> **Note:** Add your project screenshots here to showcase the application.

| Feature | Screenshot |
|---------|------------|
| Welcome Screen | ![Welcome](screenshots/welcome.png) |
| Chat Interface | ![Chat](screenshots/chat.png) |
| API Documentation | ![API](screenshots/api.png) |

---

## 🏗️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (React)                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐ │
│  │  App.jsx    │  │  Chat UI    │  │  Axios API Client       │ │
│  └──────┬──────┘  └──────┬──────┘  └───────────┬─────────────┘ │
└─────────┼────────────────┼─────────────────────┼───────────────┘
          │                │                     │
          │                │                     │
          ▼                ▼                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                          │
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │                    ChatController                          │ │
│  │  /api/ask  │  /api/chat  │  /api/history  │  /api/health  │ │
│  └───────────────────────────┬────────────────────────────────┘ │
│                              │                                   │
│  ┌───────────────────────────▼────────────────────────────────┐ │
│  │                      ChatService                            │ │
│  │  • getResponse()  • getChatHistory()  • clearHistory()    │ │
│  └───────────────────────────┬────────────────────────────────┘ │
│                              │                                   │
│  ┌───────────────────────────▼────────────────────────────────┐ │
│  │                    Spring AI Client                         │ │
│  │              (Google Gemini API)                           │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │              Exception Handling & Logging                  │ │
│  │  GlobalExceptionHandler  │  SLF4J Logger                   │ │
│  └────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💡 Key Implementation Details

### Clean Architecture
The project follows **Controller → Service → DTO** pattern:
- **Controllers**: Handle HTTP requests/responses
- **Services**: Contain business logic and AI integration
- **DTOs**: Data transfer objects for request/response validation

### Error Handling
- Custom exception (`AIChatException`) with proper error codes
- Global exception handler returning structured JSON error responses
- Input validation using Jakarta Bean Validation

### Chat History
- In-memory storage using `ConcurrentHashMap` for thread safety
- Maximum 10 conversation pairs per user to manage memory
- Session-based tracking with unique user IDs

---

## 🔮 Future Improvements

This project has a solid foundation and can be extended with:

- **🔐 JWT Authentication** - Add secure user authentication
- **🗄️ Database Integration** - Persist chat history using PostgreSQL/MongoDB
- **📄 RAG (Retrieval-Augmented Generation)** - Document-based AI for specialized queries
- **🌐 Multi-language Support** - Translate conversations
- **📱 Mobile App** - React Native or Flutter integration
- **🔌 WebSocket Support** - Real-time streaming responses
- **🧪 Unit Tests** - Increase test coverage with JUnit and Mockito

---

## 📊 Learning Outcomes

This project demonstrates proficiency in:

- ✅ Full-stack web development
- ✅ Spring Boot framework ecosystem
- ✅ RESTful API design and implementation
- ✅ Integration with external AI APIs
- ✅ Modern frontend development with React
- ✅ Clean code and best practices
- ✅ Version control with Git

---

## 📄 License

This project is for educational and demonstration purposes.

---

## 👨‍💻 Author

**Your Name**  
B.Tech CSE Student

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/yourprofile)
[![GitHub](https://img.shields.io/badge/GitHub-333333?style=flat&logo=github&logoColor=white)](https://github.com/yourusername)

---

<div align="center">

**⭐ Star this repository if you found it helpful!**

*Built with ❤️ using Spring Boot + React + Google Gemini*

</div>
