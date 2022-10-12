package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SetRedisHeaders implements Processor {

    /**
     * Set the Redis Key value for lookup from the JSON AccountPayload
     * @param exchange
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();


        AccountPayload payload = mapper.readValue(exchange.getIn().getBody().toString(),AccountPayload.class);
        exchange.getIn().setHeader("CamelRedis.Key",payload.getAccountNo());
        exchange.getIn().setHeader("CamelRedis.Value",exchange.getIn().getBody().toString());

    }

}