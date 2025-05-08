# Learning Management System (LMS)

## Overview

The **Learning Management System (LMS)** is a comprehensive educational platform designed to facilitate online learning through course management, student enrollment, assessment delivery, and progress tracking. It provides distinct interfaces for instructors and students, ensuring effective content management and user interaction.

---

## Key Features

### Course Management
- Course creation and organization by instructors
- Lesson and media content management
- Support for documents, videos, and other media types  

### User Management
- Role-based access control (Student, Instructor)
- User profile management
- Authentication and authorization  

### Enrollment System
- Student enrollment in courses
- Course roster management for instructors
- Enrollment notifications  

### Assessment System
- Quiz creation and management
- Assignment creation and submission
- Reusable question bank  

### Progress Tracking
- Student progress monitoring
- Course completion tracking
- Performance analytics  

### Notification System
- Event-driven notifications
- Email alerts for key events
- Course-specific announcements  

---

## System Architecture

The LMS follows a modular, layered architecture:

### Presentation Layer (Controllers)
Handles HTTP requests, input validation, and user authentication. Communicates with the business layer through the ServiceFacade.  

### Business Layer (Services)
Encapsulates business logic. The `ServiceFacade` aggregates services to simplify controller interaction.  

### Data Access Layer (Repositories)
Manages persistence and retrieval. The `RepositoryFacade` consolidates repositories for the business layer.  

---

## Component Details

### Course Management
Allows instructors to create and organize courses with lessons and media.  

### Quiz System
Instructors can create quizzes using a question bank; students receive instant feedback.  

### Assignment System
Supports assignment creation and student submissions.  

### Enrollment System
Manages student course enrollments and notifications.  

---

## API Endpoints

### User Management
- `POST /auth/register` - Register a new user  
- `POST /auth/login` - Authenticate a user  
- `GET /users/me` - Get current user info  
- `PATCH /users/updateInfo` - Update user profile  

### Course Management
- `POST /courses` - Create a new course  
- `POST /courses/{courseId}/media` - Upload media  
- `GET /courses/{courseId}/media` - Retrieve media  
- `POST /courses/{courseId}/lessons` - Add a lesson  
- `GET /courses/{courseId}/lessons` - Get lessons  
- `GET /courses/availableCourses` - List available courses  

### Enrollment
- `POST /enrollments/enroll` - Enroll student in course  
- `GET /enrollments/course/{courseId}` - View course enrollments  

### Quiz Management
- `POST /quizzes` - Create a quiz  
- `GET /quizzes` - List all quizzes  
- `DELETE /quizzes/{quizId}` - Delete a quiz  
- `POST /quizzes/{quizId}/submit` - Submit a quiz attempt  

### Assignment Management
- `POST /course/{courseId}/assignments/create` - Create assignment  
- `POST /assignments/{assignmentId}/submissions/submit` - Submit assignment  

### Question Bank
- `POST /questionBank/{courseId}/add` - Add questions to bank  

### Progress Tracking
- `GET /progress/students` - Get all students' progress  
- `GET /progress/students/{studentId}` - Get progress for a specific student  

---


## Authentication and Authorization

Implements Role-Based Access Control (RBAC) with controller-level enforcement. Authenticated users are granted permissions based on their role (Student or Instructor).  

---

