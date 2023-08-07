package BlogAssignmentClient;

import BlogAssignmentClient.api.BlogPost;
import BlogAssignmentClient.client.BlogPostClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private BlogPostClient blogPostsClient;
    private String credentials;
    Scanner sc = new Scanner(System.in);
    public Menu(BlogPostClient blogPostClient, String credentials) {
        this.blogPostsClient = blogPostClient;
        this.credentials = credentials;
    }
    //display menu
    public void start(){
        System.out.println("1. Get all blog posts\n" +
                "2. Get get blog post by id\n" +
                "3. Search blog posts\n" +
                "4. Update blog post title/content\n" +
                "5. Create blog post\n" +
                "6. Delete blog post\n"+
                "7. Exit");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                this.getAllBlogPosts();
                break;
            case 2:
                System.out.println("Enter id: ");
                int id = sc.nextInt();
                sc.nextLine();
                this.getBlogPostById(id);
                break;
            case 3:
                System.out.println("Enter query: ");
                String query = sc.nextLine();
                this.searchBlogPost(query);
                break;
            case 4:
                System.out.println("Enter id: ");
                id = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter title: ");
                String title = sc.nextLine();
                System.out.println("Enter content: ");
                String content = sc.nextLine();
                this.updateBlogPost(id, title, content);
                break;
            case 5:
                System.out.println("Enter title: ");
                 title = sc.nextLine();
                System.out.println("Enter content: ");
                 content = sc.nextLine();
                this.createBlogPost(title, content);
                break;
            case 6:
                System.out.println("Enter id: ");
                id = sc.nextInt();
                sc.nextLine();
                this.deleteBlogPost(id);
                break;
            case 7:
                System.exit(0);

        }

    }
    //get all blog posts
    public void getAllBlogPosts() {
        for (BlogPost bp : blogPostsClient.getAllBlogPosts()) {
            this.printBlogPost(bp);
        }
        this.start();
    }
    //get blog post by id
    public void getBlogPostById(int id){
        BlogPost blogPost = blogPostsClient.getBlogPostById(id);
        if(blogPost==null) {
            System.out.println("Blog post with this id does not exist");
        }
        else {
            this.printBlogPost(blogPost);
        }
        this.start();
    }
    //search blog posts
    public void searchBlogPost(String query){
        List<BlogPost> res = blogPostsClient.searchBlogPosts(query);
        if(res.isEmpty()){
            System.out.println("No blog posts matching the query");
        }
        else {
            for (BlogPost bp :res) {
                this.printBlogPost(bp);
            }
        }
        this.start();
    }
    //update blog post title/content
    public void updateBlogPost(int id, String title, String content){
        title = title.equals("")?null:title;
        content = content.equals("")?null:content;
        Map<String, String> newPost = new HashMap<>();
        newPost.put("title", title);
        newPost.put("content", content);
        blogPostsClient.updateBlogPost(credentials,id, newPost);
        this.start();
    }
    //create blog post
    public void createBlogPost(String title, String content){
        Map<String, String> newPost = new HashMap<>();
        newPost.put("title", title);
        newPost.put("content", content);
        blogPostsClient.createNewBlogPost(credentials,newPost);
        this.start();
    }
    //delete blog post
    public void deleteBlogPost(int id){
        blogPostsClient.deleteBlogPost(credentials, id);
        this.start();
    }
    //print blog post
    public void printBlogPost(BlogPost bp){
        System.out.println("ID: "+ bp.getId());
        System.out.println("Title: "+ bp.getTitle());
        System.out.println("Content: "+bp.getContent());
        System.out.println("Date Created: "+bp.getDateCreated());
    }


}
