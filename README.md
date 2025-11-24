# 🎓 AI Powered Exam Prep Service

This project is a Spring Boot-based backend service designed to automate exam preparation processes for students and educators. It leverages **Google Gemini AI** integration to generate topic-based questions and automatically converts them into **Google Forms** quizzes via **Google Apps Script**.

## 🚀 Key Features

* **AI-Driven Question Generation:** Generates question sets (mapped to POJOs) on desired topics and difficulty levels using the Google Gemini model.
* **Automated Google Form Creation:** Instantly converts generated questions into ready-to-solve online quiz links through a custom Google Apps Script integration.
* **User & Chat Management:** Stores user chat history and AI interactions securely on MongoDB.
* **Secure Authentication:** Implements secure registration and login processes using Spring Security and BCrypt.
* **Reactive External Calls:** Utilizes `WebClient` to perform asynchronous requests to AI and Script services.
* **HTML Parsing:** Handles redirect responses and data extraction from external services using `Jsoup`.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x (Web, Security)
* **Database:** MongoDB (Spring Data MongoDB)
* **AI Integration:** Google Gemini API
* **Scripting:** Google Apps Script (GAS)
* **Tools:** Docker, Maven, Lombok, Jsoup, Jackson
* **Client:** WebClient (Reactive)

## ⚙️ Setup and Execution

Follow the steps below to run the project in your local environment.

### 1. Prerequisites
* JDK 17 or higher
* Maven
* MongoDB (Local installation or Docker)
* Google AI Studio API Key

### 2. Environment Variables
To run the project, you must add the following variables to your IDE configurations or an `.env` file:

```properties
GEMINI_API_KEY=AIzaSyD....
MONGODB_URI=mongodb://localhost:27017
# Google Apps Script Deployment URL
google_app_script_url=[https://script.google.com/macros/s/..../exec](https://script.google.com/macros/s/..../exec)
