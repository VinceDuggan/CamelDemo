package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SplitProcessor implements Processor {


    public void process(Exchange exchange) throws Exception {

        var i = exchange.getIn().getBody().toString();
        //System.out.println(i);


    }

}
