package co.edu.sena.tu_unidad;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.TimeZone;

@SpringBootApplication
public class TuUnidadApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TuUnidadApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC-5"));
    }
}


// 