package cl.demo.camel.procesos;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelDemoApplication.class, args);
	}

	// Rutas definidas como @Bean para evitar problemas de escaneo
	@Bean
	RouteBuilder routes() {
		return new RouteBuilder() {
			@Override
			public void configure() {

				// ping para ver que Camel corre
				from("timer:saludo?period=5000")
						.setBody(simple("Hola desde Camel @ ${date:now:yyyy-MM-dd HH:mm:ss}"))
						.to("log:saludos");

				// Consumer HTTP directo: GET http://localhost:8082/hola
				from("platform-http:/hola?httpMethodRestrict=GET")
						.setHeader("Content-Type", constant("application/json"))
						.setBody(simple("{\"msg\":\"Hola Camel!\",\"time\":\"${date:now:yyyy-MM-dd HH:mm:ss}\"}"));
			}
		};
	}

}
