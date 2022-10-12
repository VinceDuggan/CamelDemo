package com.example.cameldemogradle.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RESTRoute extends RouteBuilder {
    /**
     * Simple GET endpoint to query an account
     *
     * http://localhost:8080/camel/api/accounts/{accountno}
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
                //.host("localhost").port(8080);

        rest("/api/accounts/{accountno}")
                .produces("text/plain")
                .get()

                .to("direct:getAccounts");
    }
}
