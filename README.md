# 💰 AI-Powered Budget Tracking Backend

This is a **Spring Boot–based backend project** designed to help you **maintain your budget** and **track your daily or monthly expenses** efficiently.  
It provides RESTful APIs for managing transactions and user authentication using **JWT-based sessions**.

Additionally, this backend integrates **LLM-powered analytics**, which analyze your financial data to generate **AI-driven insights and reports** about your spending habits.

---

## 🚀 Features

- 🔐 **User Authentication** using JWT (Signup & Login)
- 💵 **Transaction Management** (Add, Update, Delete)
- 📊 **AI Insights** – LLM-based monthly or daily expense analysis
- 🗄️ **Database Integration** using Spring Data JPA
- ⚙️ **REST APIs** accessible via Postman

---

## ⚡ Getting Started

### Step 1: Clone the Repository

```bash
git clone https://github.com/your-username/budget-tracker-backend.git
cd budget-tracker-backend

Step 2: Run the Application

Make sure you have Java 21+ and Maven installed.

mvn spring-boot:run


The server will start at:

http://localhost:8080

🧪 Test the APIs with Postman

You can use the following steps to test all APIs using Postman:

🧍 Step 1: Signup

API Endpoint:
POST http://localhost:8080/auth/signup

Sample Request Body:

{
    "name": "user",
    "email": "user@1234",
    "password": "123456789"
}


This will create a new user in the system.

🔑 Step 2: Login

API Endpoint:
POST http://localhost:8080/auth/login

Sample Request Body:

{
    "email": "ankitsingh@1234",
    "password": "12345678"
}


On successful login, you’ll receive a JWT Token which will be used to authenticate all subsequent requests.

💸 Step 3: Add a Transaction

API Endpoint:
POST http://localhost:8080/api/transactions

Sample Request Body:

{
    "title": "Groceries",
    "description": "Bought Masala",
    "amount": 199,
    "category": "Food",
    "date": "2025-05-18"
}


This will save the transaction details in the database.

✏️ Step 4: Update a Transaction

API Endpoint:
PUT http://localhost:8080/api/{transactionId}

Sample Request Body:

{
    "title": "Groceries",
    "description": "Bought Masala",
    "amount": 200,
    "category": "Food",
    "date": "2025-05-18"
}


Replace {transactionId} with the actual ID of the transaction you want to update.

❌ Step 5: Delete a Transaction

API Endpoint:
DELETE http://localhost:8080/api/{transactionId}

This will remove the transaction from the database.

🧠 AI-Powered Insights

The project uses Large Language Models (LLMs) to analyze your transaction data and generate smart insights, such as:

Category-wise expense summaries

Spending trends over time

Personalized suggestions for saving more effectively

🛠️ Tech Stack

Backend: Spring Boot (Java)

Database: H2 / MySQL (configurable)

Security: Spring Security with JWT

AI Layer: LLM Integration for Expense Insights

Tools: Postman for API testing

📫 Author

👨‍💻 Ankit Singh
B.Tech in Computer Science Engineering – Maharaja Surajmal Institute of Technology
🔗 LinkedIn
 | ✉️ ankitsingh210620002@gmail.com
