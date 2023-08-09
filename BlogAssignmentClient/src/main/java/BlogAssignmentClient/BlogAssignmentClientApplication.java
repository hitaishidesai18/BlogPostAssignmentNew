package BlogAssignmentClient;

import BlogAssignmentClient.api.BlogPost;
import BlogAssignmentClient.api.BlogPostIn;
import BlogAssignmentClient.client.BlogPostClient;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class BlogAssignmentClientApplication extends Application<BlogAssignmentClientConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlogAssignmentClientApplication().run(args);
    }

    @Override
    public String getName() {
        return "BlogAssignmentClient";
    }

    @Override
    public void initialize(final Bootstrap<BlogAssignmentClientConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BlogAssignmentClientConfiguration configuration,
                    final Environment environment) throws IOException {


        String username = "user";
        String password = "password";
        BlogPostClient blogPostsClient = Feign.builder().client(new OkHttpClient()).encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(BlogPostClient.class, "http://localhost:8080/blog-post");

        String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Menu menu = new Menu(blogPostsClient, credentials);
        menu.start();
    }

}
