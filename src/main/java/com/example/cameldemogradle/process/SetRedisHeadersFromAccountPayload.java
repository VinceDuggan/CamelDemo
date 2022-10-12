package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SetRedisHeadersFromAccountPayload implements Processor {


    public void process(Exchange exchange) throws Exception {

        var mapper = new ObjectMapper();

        AccountPayload payload = null;
        payload = (AccountPayload) exchange.getIn().getBody();
        if (payload.getAmount() > 10000) {   //stop test data getting too big
            payload.setAmount(10);
        }
        exchange.getIn().setHeader("CamelRedis.Key",payload.getAccountNo());
        exchange.getIn().setHeader("CamelRedis.Value",mapper.writeValueAsString(payload));

    }

}