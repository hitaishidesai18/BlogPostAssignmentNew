package BlogPostAssignment.db;

import BlogPostAssignment.api.BlogPost;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogPostDao {
    @SqlQuery("SELECT * FROM blog_posts;")
    @RegisterBeanMapper(BlogPost.class)
    List<BlogPost> getAllBlogPosts();

    @SqlQuery("SELECT id, title, content, time_created FROM blog_posts WHERE id = :id;")
    @RegisterBeanMapper(BlogPost.class)
    BlogPost getBlogPostById(@Bind("id") int id);

    @SqlQuery("SELECT id, title, content, time_created FROM blog_posts WHERE title LIKE :query OR content LIKE :query;")
    @RegisterBeanMapper(BlogPost.class)
    List<BlogPost> searchBlogPosts(@Bind("query") String query);

    @SqlUpdate("INSERT INTO blog_posts (title, content, time_created) VALUES (:title, :content, CURDATE());")
    void createBlogPost(@Bind("title") String title, @Bind("content") String content);

    @SqlUpdate("UPDATE blog_posts SET title = :newTitle WHERE id = :id;")
    void updateBlogPostTitle(@Bind("newTitle") String newTitle, @Bind("id") int id);

    @SqlUpdate("UPDATE blog_posts SET content = :newContent WHERE id = :id;")
    void updateBlogPostContent(@Bind("newContent") String newContent, @Bind("id") int id);

    @SqlUpdate("DELETE FROM blog_posts WHERE id = :id;")
    void deleteBlogPost(@Bind("id") int id);
}
