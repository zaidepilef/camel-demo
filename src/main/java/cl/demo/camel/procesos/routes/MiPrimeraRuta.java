package cl.demo.camel.procesos.routes;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

public class MiPrimeraRuta extends RouteBuilder {
    @Override
  public void configure() {

    from("timer:saludo?period=5000")
      .setBody(simple("Hola desde Camel @ ${date:now:yyyy-MM-dd HH:mm:ss}"))
      .to("log:saludos");

    restConfiguration().component("platform-http").bindingMode(RestBindingMode.off);

    rest("/hola").get().produces("application/json").to("direct:saludo");

    from("direct:saludo")
      .setHeader("Content-Type", constant("application/json"))
      .setBody(simple("{\"msg\":\"Hola Camel!\",\"time\":\"${date:now:yyyy-MM-dd HH:mm:ss}\"}"));
  }
}
