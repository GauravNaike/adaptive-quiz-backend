# 🧠 Adaptive Quiz Platform

A full-stack Adaptive Quiz System built with:

- ⚙️ Spring Boot (Backend)
- 🔐 JWT Authentication
- 🐘 PostgreSQL
- 🔴 Redis
- ⚛️ React (Frontend - Vite)
- 🐳 Docker & Docker Compose

---

## 🚀 Features

- User Signup & Login (JWT Based)
- Adaptive Difficulty Quiz Engine
- Leaderboard System (Redis)
- User Statistics Tracking
- Dockerized Full Backend
- Modern React Frontend

---

## 🏗️ Project Structure

adaptive-quiz/
│
├── src/ # Spring Boot Backend
├── frontend/ # React Frontend (Vite)
├── docker-compose.yml
├── Dockerfile
└── pom.xml


---

# 🐳 Run Backend (Docker)

From root folder:

```bash
docker compose up --build

Backend runs at:

http://localhost:8080

# Run Frontend

Open new terminal:

cd frontend
npm install
npm run dev

Frontend runs at: http://localhost:5173

🔐 API Endpoints

Authentication
POST /auth/signup
POST /auth/login

Quiz
GET  /v1/quiz/next?userId=1
POST /v1/quiz/answer
GET  /v1/quiz/stats?userId=1

Leaderboard
GET /v1/leaderboard/top

Tech Stack

Layer	Technology
Backend	Spring Boot 4
Security	Spring Security + JWT
Database	PostgreSQL
Cache	Redis
Frontend	React + Vite
DevOps	Docker

👨‍💻 Author

Gaurav Naike
