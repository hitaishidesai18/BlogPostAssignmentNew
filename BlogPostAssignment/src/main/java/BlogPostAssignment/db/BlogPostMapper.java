package BlogPostAssignment.db;

import BlogPostAssignment.api.BlogPost;
import com.mysql.cj.result.Row;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogPostMapper implements RowMapper<BlogPost> {

    @Override
    public BlogPost map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new BlogPost(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getDate("time_created")
        );
    }
}
