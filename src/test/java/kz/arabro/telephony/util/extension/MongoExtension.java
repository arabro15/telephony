package kz.arabro.telephony.util.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoExtension implements Extension {

    static {
        MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
        mongoDBContainer.start();

        System.setProperty("spring.data.mongodb.host", mongoDBContainer.getHost());
        System.setProperty("spring.data.mongodb.port", String.valueOf(mongoDBContainer.getFirstMappedPort()));
        System.setProperty("spring.data.mongodb.username", "");
        System.setProperty("spring.data.mongodb.password", "");
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getConnectionString());
    }

}
