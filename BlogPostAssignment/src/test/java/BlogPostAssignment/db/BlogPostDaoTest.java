package BlogPostAssignment.db;

import BlogPostAssignment.api.BlogPost;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jdbi.v3.core.Jdbi;


import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BlogPostDaoTest {
    private Jdbi jdbi;
    private BlogPostDao blogPostDao;
    @Before
    public void setUp(){
        jdbi = Jdbi.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE blog_posts (id INTEGER PRIMARY KEY AUTO_INCREMENT, title VARCHAR,content VARCHAR, time_created DATETIME )");
        });
        blogPostDao = jdbi.onDemand(BlogPostDao.class);
        blogPostDao.createBlogPost("title 1", "content 1");
        blogPostDao.createBlogPost("title 2", "content 2");

    }

    @Test
    public void getBlogPostById(){
        BlogPost bp1 = blogPostDao.getBlogPostById(1);
        assertEquals("title 1", bp1.getTitle());
        assertEquals("content 1", bp1.getContent());

        BlogPost bp2 = blogPostDao.getBlogPostById(99);
        assertEquals(null, bp2);

    }
    @Test
    public void createBlogPost(){
        String title = "test title";
        String content = "test content";
        int id =  blogPostDao.createBlogPost(title, content);
        BlogPost bp = blogPostDao.getBlogPostById(id);
        assertEquals(title, bp.getTitle());
        assertEquals(content, bp.getContent());
    }
    @Test
    public void searchBlogPosts(){
        String query1 = "%title%";
        assertEquals(2, blogPostDao.searchBlogPosts(query1).size());
        String query2 = "%apple%";
        assertEquals(0, blogPostDao.searchBlogPosts(query2).size());

    }
    @Test
    public void getAllBlogPosts(){
        assertEquals(2, blogPostDao.getAllBlogPosts().size());
        blogPostDao.createBlogPost("new title", "new content");
        assertEquals(3, blogPostDao.getAllBlogPosts().size());
    }

    @Test
    public void updateBlogPostTitle(){
        int id = blogPostDao.createBlogPost("test title","test content");
        String newTitle = "updated title";
        blogPostDao.updateBlogPostTitle(newTitle, id);
        assertEquals(newTitle, blogPostDao.getBlogPostById(id).getTitle());
    }

    @Test
    public void updateBlogPostContent(){
        int id = blogPostDao.createBlogPost("test title","test content");
        String newContent = "updated content";
        blogPostDao.updateBlogPostContent(newContent, id);
        assertEquals(newContent, blogPostDao.getBlogPostById(id).getContent());
    }

    @Test
    public void deleteBlogPost(){
        int id = blogPostDao.createBlogPost("test title","test content");
        blogPostDao.deleteBlogPost(id);
        assertEquals(null, blogPostDao.getBlogPostById(id));
    }
    @After
    public void tearDown(){
        jdbi.useHandle(handle -> {
            handle.execute("DROP TABLE blog_posts");
        });
    }
}



