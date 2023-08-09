package BlogPostAssignment;

import BlogPostAssignment.api.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
public class BlogAssignmentAuthenticator implements Authenticator<BasicCredentials, User> {
        @Nullable
        @Override
        public Optional<User> authenticate (BasicCredentials creds){
            if("password".equals(creds.getPassword())){
                return Optional.of(new User(creds.getUsername()));
            }
            else return Optional.empty();
        }
    }

