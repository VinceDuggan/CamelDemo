package com.example.cameldemogradle.route;

import com.example.cameldemogradle.process.GenAccountInfoProcessor;
import com.example.cameldemogradle.AccountPayload;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;



@Component
class GenTestDataRoute extends RouteBuilder {

    @Override
    public void configure()  {

        from("timer:getData?delay=5000&period=100")
                .routeId("GenTestDataRoute")
                .process(new GenAccountInfoProcessor())
                .marshal().json(AccountPayload.class)
                .setHeader("kafka.KEY", constant("TestData"))
                .to("kafka:event1?brokers={{account.kafka.host}}");

    }
}