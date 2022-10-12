package com.example.cameldemogradle.route;

import com.example.cameldemogradle.aggregate.RedisAggregator;
import com.example.cameldemogradle.process.SetRedisHeaders;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RedisAggregatorRoute extends RouteBuilder {


    @Override
    public void configure() {
        from("kafka:event2?brokers={{account.kafka.host}}")
                .routeId("RedisAggregatorRoute")
                //.log("RedisAggregatorRoute  :  ${body}")
                .process( new SetRedisHeaders())
                .enrich("spring-redis://{{account.redis.host}}?command=GET")
                .aggregate(constant("always"), new RedisAggregator()).completionSize(100)
                .to("direct:persist")
                .end();

    }
}