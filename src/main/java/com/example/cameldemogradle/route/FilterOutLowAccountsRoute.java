package com.example.cameldemogradle.route;

import com.example.cameldemogradle.rule.HighAccount;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Sample route demonstrating filter
 */

@Component
class FilterOutLowAccountsRoute extends RouteBuilder {

    Predicate isHighAccount = new HighAccount();

    @Override
    public void configure() {
        from("kafka:event1?brokers={{account.kafka.host}}&fetchMinBytes=2000")
                .routeId("FilterOutLowAccountsRoute")
                .choice()
                  .when(isHighAccount)
//                    .log("${headers[kafka.KEY]} : ${body}")
                    .to("kafka:event2?brokers={{account.kafka.host}}")
  //              .otherwise()
  //                 .log("Ignoring low account")
                .end();

    }
}