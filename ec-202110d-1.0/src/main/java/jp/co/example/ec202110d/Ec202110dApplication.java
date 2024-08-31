package jp.co.example.ec202110d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Ec202110dApplication extends SpringBootServletInitializer {

        public static void main(String[] args) {
                SpringApplication.run(Ec202110dApplication.class, args);
        }
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ec202110dApplication.class);
        }
}
