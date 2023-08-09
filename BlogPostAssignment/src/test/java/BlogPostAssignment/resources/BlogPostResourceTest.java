package BlogPostAssignment.resources;

import BlogPostAssignment.api.User;
import BlogPostAssignment.db.BlogPostDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class BlogPostResourceTest {
    private BlogPostDao blogPostDao;
    private BlogPostResource resource;
    private User user;

    @Before
    public void setUp(){
         blogPostDao = mock(BlogPostDao.class);
         resource = new BlogPostResource(blogPostDao);
         user = mock(User.class);
    }
    @Test
    public void getAllBlogPosts(){
        resource.getAllBlogPosts();
        verify(blogPostDao).getAllBlogPosts();
    }

    @Test
    public void getBlogPostById(){
        int id = 4;
        resource.getBlogPostById(id);
        verify(blogPostDao).getBlogPostById(id);
    }

    @Test
    public void searchBlogPosts(){
        String query = "test";
        resource.searchBlogPosts(query);
        verify(blogPostDao).searchBlogPosts("%"+query+"%");
    }

    @Test
    public void createBlogPost(){
        String title = "test title";
        String content = "test content";
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("content", content);
        resource.createBlogPost(user, data);
        verify(blogPostDao).createBlogPost(title, content);
    }

    @Test
    public void updateBlogPost(){
        String title = "test title";
        String content = "test content";
        int id = 8;
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("content", content);
        resource.updateBlogPost(user, id, data);
        verify(blogPostDao).updateBlogPostContent(content,id);
        verify(blogPostDao).updateBlogPostTitle(title, id);
    }

    @Test
    public void updateBlogPostTitle(){
        String title = "test title";
        int id = 8;
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("content", null);
        resource.updateBlogPost(user, id, data);
        verify(blogPostDao, never()).updateBlogPostContent(Mockito.anyString(), Mockito.anyInt());
        verify(blogPostDao).updateBlogPostTitle(title, id);
    }

    @Test
    public void updateBlogPostContent(){
        String content = "test content";
        int id = 8;
        Map<String, String> data = new HashMap<>();
        data.put("title", null);
        data.put("content", content);
        resource.updateBlogPost(user, id, data);
        verify(blogPostDao).updateBlogPostContent(content, id);
        verify(blogPostDao, never()).updateBlogPostTitle(Mockito.anyString(), Mockito.anyInt());
    }

    @Test
    public void dontUpdateBlogPost(){
        int id = 8;
        Map<String, String> data = new HashMap<>();
        data.put("title", null);
        data.put("content", null);
        resource.updateBlogPost(user, id, data);
        verify(blogPostDao, never()).updateBlogPostContent(Mockito.anyString(), Mockito.anyInt());
        verify(blogPostDao, never()).updateBlogPostTitle(Mockito.anyString(), Mockito.anyInt());
    }

    @Test
    public void deleteBlogPost(){
        int id = 8;
        resource.deleteBlogPost(user, id);
        verify(blogPostDao).deleteBlogPost(id);
    }
}
