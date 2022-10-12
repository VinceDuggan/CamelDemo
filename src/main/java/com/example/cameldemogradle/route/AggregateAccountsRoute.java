package com.example.cameldemogradle.route;

import com.example.cameldemogradle.aggregate.AccountAggregator;
import com.example.cameldemogradle.process.FormatAggregationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
@Deprecated
public class AggregateAccountsRoute extends RouteBuilder {


    @Override
    public void configure() {
        from("kafka:event2?brokers={{account.kafka.host}}")
                .routeId("AggregateAccountsRoute")
                .aggregate(constant("always"), new AccountAggregator()).completionSize(100)
                .process(new FormatAggregationProcessor())
                //.log("${body}")
                .end();

    }
}