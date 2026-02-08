package az.edu.asiouconferenceportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import az.edu.asiouconferenceportal.config.FileStorageProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableScheduling
public class AsiouConferencePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsiouConferencePortalApplication.class, args);
    }

}
