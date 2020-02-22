package werls.scis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScisApplication.class, args);
    }

}
