package co.edu.sena.tu_unidad;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TuUnidadApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TuUnidadApplication.class)
				.web(WebApplicationType.SERVLET) // 👈 Fuerza aplicación web
				.run(args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC-5"));
	}

}
