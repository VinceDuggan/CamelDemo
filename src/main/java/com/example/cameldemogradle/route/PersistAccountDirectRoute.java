package com.example.cameldemogradle.route;

import com.example.cameldemogradle.process.SetRedisHeadersFromAccountPayload;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


//@Component
public class PersistAccountDirectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:persist")
                .split(body())
                .setBody(simple("${body.value}"))
                //.log("direct:persist  :  ${body}")
                .process(new SetRedisHeadersFromAccountPayload())
                .to("spring-redis://{{account.redis.host}}?command=SET")
                .log("SAVED:  ${body}");
    }
}
