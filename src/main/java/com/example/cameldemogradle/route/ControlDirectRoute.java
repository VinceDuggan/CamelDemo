package com.example.cameldemogradle.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ControlDirectRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
         from("direct:control")
                 .process( e -> {
                     CamelContext context = e.getContext();
                     context.getRouteController().stopRoute("GenTestDataRoute", 2, TimeUnit.SECONDS);
                     context.stop();
                 });

    }
}
