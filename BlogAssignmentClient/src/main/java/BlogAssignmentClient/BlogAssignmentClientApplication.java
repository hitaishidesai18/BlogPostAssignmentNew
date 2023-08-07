package BlogAssignmentClient;

import BlogAssignmentClient.api.BlogPost;
import BlogAssignmentClient.api.BlogPostIn;
import BlogAssignmentClient.client.BlogPostClient;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class BlogAssignmentClientApplication extends Application<BlogAssignmentClientConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlogAssignmentClientApplication().run(args);
    }

    @Override
    public String getName() {
        return "BlogAssignmentClient";
    }

    @Override
    public void initialize(final Bootstrap<BlogAssignmentClientConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BlogAssignmentClientConfiguration configuration,
                    final Environment environment) {


        String username = "user";
        String password = "password";
        BlogPostClient blogPostsClient = Feign.builder().client(new OkHttpClient()).encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(BlogPostClient.class, "http://localhost:8080/blog-post");

        String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Menu menu = new Menu(blogPostsClient, credentials);
        menu.start();
//        System.out.println("List all blog posts-");
//        for(BlogPost bp: blogPostsClient.getAllBlogPosts()){
//            System.out.println("ID: "+ bp.getId());
//            System.out.println("Title: "+ bp.getTitle());
//            System.out.println("Content: "+bp.getContent());
//            //System.out.println("Date Created: "+ bp.getDateCreated()+"\n");
//        }
////
////        //List blog post by id
////        System.out.println("List blog post by id-");
////        BlogPost blogPost = blogPostsClient.getBlogPostById(2);
////        System.out.println("ID: "+ blogPost.getId()+"\n");
////
////        //Search blog post
////        System.out.println("Search blog post for first-");
////        for(BlogPost bp: blogPostsClient.searchBlogPosts("first")){
////            System.out.println("ID: "+ bp.getId());
////            System.out.println("Title: "+ bp.getTitle());
////            System.out.println("Content: "+bp.getContent());
////            //System.out.println("Date Created: "+ bp.getDateCreated()+"\n");
////        }
////
//        //create new blog post
//        Map<String, String> newPost = new HashMap<>();
//        newPost.put("title", "brand new post");
//        newPost.put("content", "brand new content");
//        System.out.println("Create new blog post-\n");
//        blogPostsClient.createNewBlogPost(credentials,newPost);
//
//        System.out.println("Update blog post-");
//
//        Map<String, String> updateData = new HashMap<>();
//        updateData.put("title", "updated title");
//        updateData.put("content", "updated content");
//        blogPostsClient.updateBlogPost(credentials,4, updateData);
////
//        System.out.println("delete blog post-");
//        //blogPostsClient.deleteBlogPost(credentials, 7);
////
//        System.out.println("List all blog posts-");
//        for(BlogPost bp: blogPostsClient.getAllBlogPosts()){
//            System.out.println("ID: "+ bp.getId());
//            System.out.println("Title: "+ bp.getTitle());
//            System.out.println("Content: "+bp.getContent());
//            //System.out.println("Date Created: "+ bp.getDateCreated()+"\n");
//        }
    }

}
