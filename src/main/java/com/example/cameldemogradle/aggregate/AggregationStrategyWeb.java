package com.example.cameldemogradle.aggregate;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class AggregationStrategyWeb implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }

        newExchange.getIn().setHeader("CamelRedis.Value", oldExchange.getIn().getHeader("CamelRedis.Value"));
        return newExchange;
    }

}