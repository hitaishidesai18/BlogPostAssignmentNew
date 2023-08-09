package BlogAssignmentClient.client;

import feign.Feign;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import jakarta.ws.rs.core.MediaType;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class BlogPostClientTest {
    private final String BASE_URL = "http://localhost:8080/blog-post";
    private BlogPostClient blogPostClient;
    private String creds;
    private int id;
    @Before
    public void setUp() throws IOException {
        String username = "user";
        String password = "password";
        blogPostClient = Feign.builder().client(new OkHttpClient()).encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(BlogPostClient.class, BASE_URL);
        creds = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Map<String, String> testPost = new HashMap<>();
        testPost.put("title", "test title");
        testPost.put("content", "test content");
        id = Integer.parseInt(new String(blogPostClient.createNewBlogPost(creds,testPost).body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
    }
    @Test
    public void getAllBlogPosts() throws IOException, InterruptedException {
        Response response = blogPostClient.getAllBlogPosts();
        assertEquals(HttpStatus.OK_200, response.status());
        String mimeType = response.headers().get("Content-Type").iterator().next();
        System.out.println(mimeType);
        assertEquals(MediaType.APPLICATION_JSON, mimeType);
    }

    @Test
    public void getBlogPostById(){
        Response response = blogPostClient.getBlogPostById(2);
        assertEquals(HttpStatus.OK_200, response.status());
        assertEquals(HttpStatus.NO_CONTENT_204, blogPostClient.getBlogPostById(69).status());
        assertEquals(MediaType.APPLICATION_JSON, response.headers().get("Content-Type").iterator().next());

    }

    @Test
    public void searchBlogPosts(){
        Response response = blogPostClient.searchBlogPosts("title");
        assertEquals(HttpStatus.OK_200, response.status());
        assertEquals(HttpStatus.OK_200, blogPostClient.searchBlogPosts("xxxx").status());
        assertEquals(MediaType.APPLICATION_JSON, response.headers().get("Content-Type").iterator().next());
    }

    @Test
    public void createBlogPost() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("title", "test title");
        data.put("content", "test content");
        Response response = blogPostClient.createNewBlogPost(creds, data);
        assertEquals(HttpStatus.OK_200, response.status());
        int created = Integer.parseInt(new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
        blogPostClient.deleteBlogPost(creds, created);
        assertEquals(MediaType.APPLICATION_JSON, response.headers().get("Content-Type").iterator().next());
    }

    @Test
    public void updateBlogPost(){
        Map<String, String> data1 = new HashMap<>();
        data1.put("title", "test update title");
        data1.put("content", "test update content");
        assertEquals(HttpStatus.NO_CONTENT_204, blogPostClient.updateBlogPost(creds, id,data1).status());

        Map<String, String> data2 = new HashMap<>();
        data2.put("title", null);
        data2.put("content", "test update content");
        assertEquals(HttpStatus.NO_CONTENT_204, blogPostClient.updateBlogPost(creds, id,data2).status());

        Map<String, String> data3 = new HashMap<>();
        data3.put("title", "test update title");
        data3.put("content", null);
        assertEquals(HttpStatus.NO_CONTENT_204, blogPostClient.updateBlogPost(creds, id,data3).status());

    }

    @Test
    public void deleteBlogPost() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("title", "test title");
        data.put("content", "test content");
        int created = Integer.parseInt(new String(blogPostClient.createNewBlogPost(creds, data).body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
        Response response = blogPostClient.deleteBlogPost(creds, created);
        assertEquals(HttpStatus.NO_CONTENT_204, response.status());
    }

    @After
    public void tearDown(){
        blogPostClient.deleteBlogPost(creds, id);
    }



}
