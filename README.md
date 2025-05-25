# AI_Resume_Analyze

[![Java](https://img.shields.io/badge/Java-23-blue)](https://www.oracle.com/java/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue)](https://www.docker.com/)
[![Keycloak](https://img.shields.io/badge/Keycloak-Security-red)](https://www.keycloak.org/)
[![MariaDB](https://img.shields.io/badge/Database-MariaDB-lightgrey)](https://mariadb.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Resume Analyzer** is a Java 23 web application that uses AI to evaluate resumes and match them with job requirements. It supports secure login with **Keycloak**, persistent storage with **MariaDB**, and is fully containerized with **Docker** for easy deployment.

---

## Features

- Secure user authentication (Keycloak)
- Resume upload and AI-based analysis
- Match resumes with job descriptions
- Admin & recruiter role management
- RESTful API with Swagger documentation
- Dockerized infrastructure for quick setup

---

## Tech Stack

- **Backend**: Java 23, Spring Boot
- **Auth**: Keycloak (OAuth2 / OIDC)
- **Database**: MariaDB
- **DevOps**: Docker, Docker Compose
- **Documentation**: Swagger / OpenAPI
- **(Optional)** AI: OpenRouter API

---

## Getting Started

### Prerequisites

- Java 23
- Docker & Docker Compose
- Git

### Clone the Project

```bash
git clone https://github.com/your-username/resume-analyzer.git
cd resume-analyzer
