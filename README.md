# 🎓 AI Powered Exam Prep Service

Hey there! 👋 This is my backend project designed to make studying a bit easier. It basically takes a topic you want to study, asks **Google Gemini AI** to create questions about it, and then automatically turns them into a **Google Forms** quiz so you can test yourself.

I built this using Spring Boot and some cool integrations like Google Apps Script.

## 🚀 What can it do?

* **Ask the AI:** You give a topic and difficulty, and the app uses Gemini to generate questions for you.
* **Auto-Quiz Maker:** This is the cool part—it sends those questions to a Google Apps Script I wrote, which instantly creates a Google Form link.
* **Chat History:** It saves your requests and the AI's answers to MongoDB so you don't lose them.
* **Security:** Basic login/register system using JWT and Spring Security.
* **Reactive Calls:** I used `WebClient` instead of RestTemplate because it handles external API calls much better.

## 🛠️ Tech Stack

Here are the tools and technologies I used:

* **Language:** Java 21 ☕
* **Framework:** Spring Boot 3.x (Web, Security)
* **Database:** MongoDB
* **AI:** Google Gemini API
* **Scripting:** Google Apps Script (GAS)
* **Libraries:** Lombok, Jsoup (for parsing HTML responses), Jackson.
* **Build Tool:** Maven

## ⚙️ How to Run Locally

If you want to run this on your machine, just follow these steps.

### 1. Prerequisites
Make sure you have these installed:

* **JDK 21** (Project runs on Java 21)
* **Maven**
* **MongoDB** (You can run it locally or use a Docker container)

### 2. Google Cloud Setup (Important!)
Since this app creates Google Forms, you need permission to access your Google Drive/Forms.

1.  Go to the [Google Cloud Console](https://console.cloud.google.com/).
2.  Create a project and enable the **Google Forms API** and **Google Drive API**.
3.  Create a Service Account or OAuth Client ID.
4.  Download the `credentials.json` file.
5.  **Place this file** inside the `src/main/resources` folder of the project.

### 3. Environment Variables
You need to set up a few configurations. You can add these to your `application.properties` file or set them up in your IDE's run configuration.

Here is what you need:

```properties
# MongoDB Connection
spring.data.mongodb.uri=mongodb://localhost:27017/exam-prep-db

# Google Gemini API Key (Get this from Google AI Studio)
GEMINI_API_KEY=AIzaSyD...

# The URL of the deployed Google Apps Script
GOOGLE_SCRIPT_URL=[https://script.google.com/macros/s/......./exec](https://script.google.com/macros/s/......./exec)

# Path to your credentials file (pointing to resources)
GOOGLE_APPLICATION_CREDENTIALS=classpath:credentials.json
