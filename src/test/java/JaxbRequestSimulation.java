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
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class JaxbRequestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8888")
            .userAgentHeader("Gatling/Performance Test");

    Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) ()
                    -> Collections.singletonMap("username", UUID.randomUUID().toString())
            ).iterator();

    ScenarioBuilder scn = CoreDsl.scenario("Load Test JAXB")
            .feed(feeder)
            .exec(http("jaxb-v0-request spring")
                    .get("/jaxb/v0/ieds")
                    .check(status().is(200))
                    //.check(header("Location").saveAs("location"))
            )
            .exec(http("jaxb-v1-request javax")
                            .get("/jaxb/v1/ieds")
                            .check(status().is(200))
            )
            .exec(http("jaxb-v3-request compas")
                            .get("/jaxb/v3/ieds")
                            .check(status().is(200))
            )
            .exec(http("jaxb-v4-request jakarta")
                            .get("/jaxb/v4/ieds")
                            .check(status().is(200))
            );

    public JaxbRequestSimulation() {

        this.setUp(scn.injectOpen(constantUsersPerSec(1).during(Duration.ofSeconds(1))))
                .protocols(httpProtocol);

    }
}

