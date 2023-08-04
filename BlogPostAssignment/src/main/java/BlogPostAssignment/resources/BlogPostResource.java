package BlogPostAssignment.resources;

import BlogPostAssignment.api.BlogPost;
import BlogPostAssignment.api.User;
import BlogPostAssignment.db.BlogPostDao;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Map;

@Path("/blog-post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogPostResource {
    private final BlogPostDao blogDao;

    public BlogPostResource(Jdbi jdbi) {
        this.blogDao = jdbi.onDemand(BlogPostDao.class);
    }
    @GET
    public List<BlogPost> getAllBlogPosts(){;
        return blogDao.getAllBlogPosts();
    }

    @GET
    @Path("/{id}")
    public BlogPost getBlogPostById(@PathParam("id") int id){
        return blogDao.getBlogPostById(id);
    }

    @GET
    @Path("/search")
    public List<BlogPost> searchBlogPosts(@QueryParam("query") String query){
        query = "%"+query+"%";
        return blogDao.searchBlogPosts(query);
    }

//    @POST
//    public void createBlogPost(@Auth User user, BlogPostIn bp){
//        blogDao.createBlogPost(bp.getTitle(), bp.getContent());
//    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBlogPost( @HeaderParam("Authorization")@Auth User user, Map<String, String> data){
        blogDao.createBlogPost(data.get("title"), data.get("content"));
    }



    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateBlogPost(@HeaderParam("Authorization")@Auth User user, @PathParam("id") int id,  Map<String, String> data) {
        if (data.get("title") != null) {
            blogDao.updateBlogPostTitle(data.get("title"), id);
        }

        if (data.get("content") != null) {
            blogDao.updateBlogPostContent(data.get("content"), id);
        }
    }

//    @PUT
//    @Path("/update/{id}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void updateBlogPost(@Auth User user, @PathParam("id") int id, @FormParam("title") String title, @FormParam("content") String content) {
//        if (title != null) {
//            blogDao.updateBlogPostTitle(title, id);
//        }
//
//        if (content != null) {
//            blogDao.updateBlogPostContent(content, id);
//        }
//    }
    @DELETE
    @Path("/{id}")
    public void deleteBlogPost(@Auth User user, @PathParam("id")int id){
        blogDao.deleteBlogPost(id);
    }
}
