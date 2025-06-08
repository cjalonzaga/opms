# OPMS - Online Portfolio Management System

## 📘 Overview

**OPMS (Online Portfolio Management System)** is a web-based content management system (CMS) designed for schools to streamline task assignment and portfolio tracking between **teachers**, **students**, and **parents**.

Teachers can create subjects and tasks, set due dates, and instantly notify students and their parents. Students can submit their responses by either uploading PDF files or typing directly into a text editor. Teachers are notified upon submission and can update the status of each student's task.

---

## 🚀 Features

### 👨‍🏫 For Teachers:
- Create and manage subjects
- Create tasks with due dates
- Notify students and parents upon task creation
- View and update task submission statuses per student

### 👩‍🎓 For Students:
- View assigned tasks
- Submit responses via:
  - PDF upload
  - Inline text editor
- Get notified of task statuses

### 👨‍👩‍👧 For Parents:
- Receive notifications about newly assigned tasks

---

## 🔐 Authentication & Authorization

- **Spring Security** handles user authentication and role-based access control.
- Redirects and view access are managed based on user roles: Teacher, Student, Parent.

---

## ☁️ File Uploads

- Previously used **AWS S3** (via a free-tier account) for secure storage of PDF submissions.
- **Removed due to security concerns** and cost monitoring challenges on free-tier usage.
- Currently, files are handled locally or can be adapted for other secure storage methods.

---

## 🛠️ Tech Stack

| Layer        | Technology Used                        |
|--------------|----------------------------------------|
| Backend      | Java, Spring Boot, Spring Security, JPA |
| Frontend     | Thymeleaf, Bootstrap, jQuery, HTML5, CSS3 |
| Database     | MySQL |
| Storage      | (Optional) Local storage (was AWS S3)    |
| Other        | AJAX for dynamic content rendering      |

---
