package ma.markware.quarkus.redis;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.scheduler.Scheduled;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RedisPublisher {

  @Inject
  RedisClient redisClient;

  @Scheduled(every = "PT5S")
  public void publishTimestamp() {
    redisClient.publish("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
  }
}
