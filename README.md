

# 🚦 Smart Traffic System (Cloud Deployed)

A **Smart Traffic Management System** built using **Spring Boot, MySQL, and REST APIs**, designed to simulate and manage traffic flow efficiently with cloud-based deployment.

🔗 **Live Demo:**
👉 [https://smart-traffic-system-deploy.onrender.com/](https://smart-traffic-system-deploy.onrender.com/)
<img width="1919" height="1000" alt="image" src="https://github.com/user-attachments/assets/8939bd6b-ad95-4f0a-9834-a139c84b7c00" />


---

## 📌 Overview

This project demonstrates how backend systems can be used to **control and manage traffic logic dynamically**. It integrates a cloud-hosted database and exposes APIs to simulate real-world traffic operations.

The goal is to **reduce congestion, automate signal logic, and enable scalable traffic control systems** using modern backend technologies.

---

## ⚙️ Tech Stack

* **Backend:** Spring Boot (Java)
* **Database:** MySQL (Railway Cloud)
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **Server:** Embedded Apache Tomcat
* **Deployment:** Render

---

## 🚀 Features

* 🚦 Traffic signal simulation logic
* 🔄 REST APIs for managing traffic flow
* 🗄️ Cloud database integration (Railway MySQL)
* ⚡ Fast backend with Spring Boot
* 🌐 Live deployment on Render
* 📁 Clean MVC architecture

---

## 🔐 Secure Configuration (Important)

Sensitive data like database credentials are **not stored in the codebase**.

Instead, the project uses **environment variables**:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### Example Environment Variables

```
DB_URL=jdbc:mysql://mainline.proxy.rlwy.net:30951/railway
DB_USERNAME=root
DB_PASSWORD=********
```

---

## ▶️ Run Locally

1. **Clone the repository**

```bash
git clone https://github.com/aryannehete14/smart-traffic-system-deploy.git
```

2. **Navigate to project**

```bash
cd project
```

3. **Set environment variables**

Windows (PowerShell):

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/traffic_db"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
```

4. **Run the application**

```bash
mvn spring-boot:run
```

5. **Open in browser**

```
http://localhost:8080
```

---

## 🌍 Deployment

* Hosted on **Render Cloud Platform**
* Uses dynamic port binding:

```properties
server.port=${PORT:8080}
```

---

## 📂 Project Structure

```
project/
 ├── src/main/java/
 │   └── SmartTrafficSystem/project/
 │       └── SmartTrafficApplication.java
 ├── src/main/resources/
 │   └── application.properties
 ├── pom.xml
```

---

## 🔌 API Endpoints (Sample)

| Method | Endpoint   | Description         |
| ------ | ---------- | ------------------- |
| GET    | `/`        | Home / Landing Page |
| GET    | `/traffic` | Fetch traffic data  |
| POST   | `/traffic` | Add traffic record  |

*(Modify based on your actual controllers if needed)*

---

## 🔥 Future Enhancements

* 🤖 AI-based traffic prediction
* 📷 Computer vision for vehicle detection
* 🚑 Emergency vehicle prioritization
* 📊 Real-time dashboard
* 📱 Frontend UI (React/Angular)

---

## 👨‍💻 Author

**Aryan Nehete**
B.Tech CSE | Backend Developer

---

## ⭐ Contribution

Contributions are welcome!
Fork the repo and submit a pull request.

---

## 📜 License

This project is licensed under the **MIT License**.

---

