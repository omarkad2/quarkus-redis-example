package ma.markware.quarkus.redis;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.RedisClientName;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RedisSubscriber {

    private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);

    @Inject
    @RedisClientName("subscriber")
    RedisClient redisClient;
    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent ev) throws IOException {
        // Define channel handlers
        vertx.eventBus().<JsonObject>consumer("io.vertx.redis.timestamp").handler(msg -> { log.info("{}", msg.body()); });

        // Subscribe to channel
        this.redisClient.subscribe(List.of("timestamp"));
    }
}
