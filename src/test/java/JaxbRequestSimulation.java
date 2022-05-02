import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class JaxbRequestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8088")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Performance Test");

    Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) ()
                    -> Collections.singletonMap("username", UUID.randomUUID().toString())
            ).iterator();

    ScenarioBuilder scn = CoreDsl.scenario("Load Test Creating Customers")
            .feed(feeder)
            .exec(http("jaxb-v0-request")
                    .post("/jaxb/v0/ieds")
                    .header("Content-Type", "application/json")
                    .check(status().is(200))
                    .check(header("Location").saveAs("location"))
            )
            .exec(http("jaxb-v1-request")
                    .get(session -> session.getString("location"))
                    .check(status().is(200))
            );

    public JaxbRequestSimulation() {
        this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15))))
                .protocols(httpProtocol);
    }
}

