package com.lms.service;

import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    private final List<Course> courseList = new ArrayList<>();  // In-memory storage for courses
    private final String mediaDirectory = "media/";  // Local directory for storing media

    public CourseServiceImpl() {
        File directory = new File(mediaDirectory);
        if (!directory.exists()) {
            directory.mkdirs();  // Create directory if it doesn't exist
        }
    }


    // private long currentId = 1; // Counter for unique IDs

    @Override
    public Course createCourse(Course course) {
        course.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6)); // Generate unique ID
        courseList.add(course);
        return course;
    }
    @Override
    public void uploadMedia(String courseId, MultipartFile file) {
        Course course = findCourseById(courseId);
        if (course != null) {
            try {
                String filePath = mediaDirectory + file.getOriginalFilename();
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);  // Save file to the local directory
                course.addMedia(filePath);  // Add file path to the course's media list
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload media file", e);
            }
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<String> getMediaForCourse(String courseId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getMediaPaths();
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course course = findCourseById(courseId);
        if (course != null) {
            course.addLesson(lesson);
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<Lesson> getLessonsForCourse(String courseId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getLessons();
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public Course findCourseById(String courseId) {
        return courseList.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courseList);
    }
}
