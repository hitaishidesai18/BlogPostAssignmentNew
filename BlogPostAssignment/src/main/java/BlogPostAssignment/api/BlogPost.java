package BlogPostAssignment.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

public class BlogPost {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("time_created")
    private DateTimeFormatter dateCreated;

    public int getId() {
        return id;
    }

    public BlogPost(int id, String title, String content, DateTimeFormatter dateCreated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public BlogPost(){}

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTimeFormatter getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTimeFormatter dateCreated) {
        this.dateCreated = dateCreated;
    }
}
