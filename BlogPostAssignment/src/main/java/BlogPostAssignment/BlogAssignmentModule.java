package BlogPostAssignment;

import com.google.inject.AbstractModule;
import io.dropwizard.auth.Authenticator;

public class BlogAssignmentModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Authenticator.class).to(BlogAssignmentAuthenticator.class);
    }


}
