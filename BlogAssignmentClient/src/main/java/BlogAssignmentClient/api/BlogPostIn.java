package BlogAssignmentClient.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogPostIn {

    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;


    public BlogPostIn(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public BlogPostIn(){}

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
}
