package com.example.cameldemogradle.process;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;

@Deprecated
public class FormatAggregationProcessor implements Processor {

    ObjectMapper mapper = new ObjectMapper();


    public void process(Exchange exchange) throws Exception {
        var list  = (HashMap<Integer, AccountPayload>)exchange.getIn().getBody();
        var listStr = new StringBuilder();

        list.forEach((k,v) -> {
            try {
                listStr.append(mapper.writeValueAsString(v)+"\n");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        exchange.getIn().setBody(listStr.toString());



    }

}
