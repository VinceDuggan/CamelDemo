package com.example.cameldemogradle.rule;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

/**
 * Arbitrary rule to demo how filters work
 * Low accounts will simply be ignored
 */
public class HighAccount implements Predicate {
        public boolean matches(Exchange exchange) {
            ObjectMapper mapper = new ObjectMapper();
            AccountPayload payload = null;
            try {
                payload = mapper.readValue( exchange.getIn().getBody().toString(), AccountPayload.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return payload.getAccountNo() > 25;
        };


}

