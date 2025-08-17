package az.edu.asiouconferenceportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AsiouConferencePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsiouConferencePortalApplication.class, args);
    }

}
