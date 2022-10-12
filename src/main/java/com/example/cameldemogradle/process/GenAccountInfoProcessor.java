package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Random;

/**
 * Generate a new account invoice - test data
 */
public class GenAccountInfoProcessor implements Processor {

    private Random r = new Random(1000);

    public void process(Exchange exchange) throws Exception {

        var i = r.nextInt(50);
        var payload = new AccountPayload(i,"Account Name "+i,10);
        exchange.getIn().setBody(payload);;
    }

}
