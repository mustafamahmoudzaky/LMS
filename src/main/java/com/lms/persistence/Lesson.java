package com.lms.persistence;

public class Lesson {
    private String  id;
    //private Long CourseId;
    private String title;
    private String content;  // URL or textual content

    public Lesson(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

}
