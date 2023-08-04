package BlogPostAssignment.api;


import java.security.Principal;

public class User implements Principal {
    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
