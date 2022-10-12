package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;

public class FormatAccountForWeb implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String value = exchange.getIn().getHeader("CamelRedis.Value", String.class);
        if (value == null) {
            exchange.getIn().setBody("Account Not Found");

        } else {
            AccountPayload payload = mapper.readValue(value, AccountPayload.class);
            exchange.getIn().setBody("Account Details : " + payload.toString());
        }


    }
}
