package com.example.cameldemogradle.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Retrieve the 'accountno' from the REST get URL and set it as the key for Redis lookup
 */

public class SetKeyHeaderFromURL implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        int val = Integer.parseInt(exchange.getIn().getHeader("accountno").toString());
        exchange.getIn().setHeader("CamelRedis.Key",val);

    }
}
