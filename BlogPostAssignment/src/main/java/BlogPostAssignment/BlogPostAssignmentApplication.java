package BlogPostAssignment;
//this is branch test
import BlogPostAssignment.api.User;
import BlogPostAssignment.health.BlogHealthCheck;
import BlogPostAssignment.resources.BlogPostResource;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;

public class BlogPostAssignmentApplication extends Application<BlogPostAssignmentConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlogPostAssignmentApplication().run(args);
    }

    @Override
    public String getName() {
        return "BlogPostAssignment";
    }

    @Override
    public void initialize(final Bootstrap<BlogPostAssignmentConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BlogPostAssignmentConfiguration configuration,
                    final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new BlogAssignmentAuthenticator())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(new BlogPostResource(jdbi));
        environment.healthChecks().register("database", new BlogHealthCheck(jdbi));

    }

}
