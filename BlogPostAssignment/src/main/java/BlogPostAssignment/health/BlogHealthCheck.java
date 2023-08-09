package BlogPostAssignment.health;

import com.codahale.metrics.health.HealthCheck;
import org.jdbi.v3.core.Jdbi;

public class BlogHealthCheck extends HealthCheck {
    private final Jdbi jdbi;

    public BlogHealthCheck(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Result check(){
        try{
            jdbi.open().getConnection().isValid(1);
            return Result.healthy();
        }
        catch (Exception e){
            return Result.unhealthy("Database connection invalid "+ e.getMessage());
        }
    }
}
