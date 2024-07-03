package org.acme;

import io.quarkus.infinispan.client.Remote;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.infinispan.client.hotrod.RemoteCache;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@Path("/greeting")
public class InfinispanGreetingResource {

    private static final Logger LOGGER = Logger.getLogger(InfinispanGreetingResource.class);

    @Inject
    @Remote("mycache")
    RemoteCache<String, Greeting> cache;

    @POST
    @Path("/{id}")
    public CompletionStage<String> postGreeting(String id, Greeting greeting) {
        LOGGER.debug("This is the postGreeting resource by Id and greeting ^^^^^^ ********** " + id + " ** " + greeting);
        return cache.putAsync(id, greeting)
                .thenApply(g -> "Greeting done!")
                .exceptionally(ex -> ex.getMessage());
    }

    @GET
    @Path("/{id}")
    public CompletionStage<Greeting> getGreeting(String id) {
        LOGGER.debug("This is the getGreeting resource by Id ^^^^^^ ********** " + id);
        return cache.getAsync(id);
    }
}
