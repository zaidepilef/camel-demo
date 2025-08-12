package cl.demo.camel.procesos.routes;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.builder.RouteBuilder;

public class MiPrimeraRuta extends RouteBuilder {
  @Override
  public void configure() {

    // ping de prueba para ver Camel vivo
    from("timer:saludo?period=5000")
        .setBody(simple("Hola desde Camel @ ${date:now:yyyy-MM-dd HH:mm:ss}"))
        .to("log:saludos");

    // Endpoint HTTP directo: GET /hola
    from("platform-http:/hola?httpMethodRestrict=GET")
        .setHeader("Content-Type", constant("application/json"))
        .setBody(simple("{\"msg\":\"Hola Camel!\",\"time\":\"${date:now:yyyy-MM-dd HH:mm:ss}\"}"));
  }
}
