package com.example.cameldemogradle.aggregate;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
/**
* Allows route to merge enrichment headers after redis GET
 */
public class AggregationStrategyWeb implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange == null) {
            return oldExchange;
        }

        ObjectMapper mapper = new ObjectMapper();

        AccountPayload payload = null;
        try {
            payload = mapper.readValue( newExchange.getIn().getBody().toString(), AccountPayload.class);
        } catch (JsonProcessingException e) {
            payload = null;
        }
        if (payload == null) {

        } else {
            newExchange.getIn().setBody("Account Details : \n" + payload.toString());

        }

        return newExchange;
    }

}