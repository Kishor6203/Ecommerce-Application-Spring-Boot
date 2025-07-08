# 🛒 E-Commerce Backend API

This is a robust and scalable backend API for an e-commerce platform, built using **Spring Boot**. It supports user authentication, product and order management, secure payments through **Razorpay** and **Stripe**, and integrates features like **JWT authentication**, **email notifications**, and more.

## 🚀 Technologies Used

- **Spring Boot** – REST API framework
- **PostgreSQL** – Relational database
- **Spring Security** – Authentication & authorization
- **JWT (JSON Web Tokens)** – Stateless authentication
- **Java Mail Sender** – Sending transactional emails
- **Razorpay & Stripe** – Payment gateway integrations

---
## 🔐 Features

- **User Authentication & Authorization**
  - Signup/Login using Spring Security and JWT
  - Role-based access control (Admin, User)
  
- **Product Management**
  - Add, update, delete, view products (Admin)
  - View products (User)

- **Cart & Order Management**
  - Add/remove items from cart
  - Checkout and order creation

- **Payments Integration**
  - Razorpay and Stripe support
  - Secure and verified transaction flow

- **Email Notifications**
  - Order confirmation emails
  - Password reset emails

---

## ⚙️ Setup Instructions

### Prerequisites

- Java 24
- Maven
- PostgreSQL
- Razorpay & Stripe accounts with API keys
