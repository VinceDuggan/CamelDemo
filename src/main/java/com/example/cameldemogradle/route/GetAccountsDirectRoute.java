package com.example.cameldemogradle.route;

import com.example.cameldemogradle.aggregate.AggregationStrategyWeb;
import com.example.cameldemogradle.process.FormatAccountForWeb;
import com.example.cameldemogradle.process.SetKeyHeaderFromURL;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Route that receives an accountno from GET URL, and fetches the account from Redis
 */
@Component
public class GetAccountsDirectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:getAccounts")
                .process(new SetKeyHeaderFromURL())
                .enrich("spring-redis://{{account.redis.host}}?command=GET")
                .aggregate(constant("always"), new AggregationStrategyWeb()).completionSize(1)
                .process(new FormatAccountForWeb())
        ;
    }
}
