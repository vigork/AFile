package team.iks.afile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(AFileApplication.class, args);
    }

}
