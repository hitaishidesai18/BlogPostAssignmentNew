package BlogAssignmentClient.client;

import BlogAssignmentClient.api.BlogPost;
import BlogAssignmentClient.api.BlogPostIn;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface BlogPostClient {
    @RequestLine("GET")
    List<BlogPost> getAllBlogPosts();

    @RequestLine("GET /{id}")
    BlogPost getBlogPostById(@Param("id") int id);

    @RequestLine("POST /")
    @Headers({"Authorization: Basic {credentials}", "Content-Type: application/json"})
    //@Body("%7B\"title\": \"{title}\", \"content\": \"{content}\"%7D")
    void createNewBlogPost(@Param("credentials") String creds, Map<String, String> data );

    @RequestLine("GET /search?query={query}")
    List<BlogPost> searchBlogPosts(@Param("query") String query);


//    @Headers("Content-Type: application/json")
//    @RequestLine("PUT /update_content/{id}?query={content}")
//    void updateBlogPostContent(@Param("id") int id, @Param("content") String content);

    @Headers({"Authorization: Basic {credentials}", "Content-Type: application/json"})
    @RequestLine("PUT /update/{id}")
    void updateBlogPost(@Param("credentials") String creds, @Param("id") int id, Map<String, String> data);
    @RequestLine("DELETE /{id}")
    @Headers("Authorization: Basic {credentials}")
    void deleteBlogPost(@Param("credentials") String creds, @Param("id") int id);

}
