package com.example.cameldemogradle.aggregate;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.HashMap;

public class AccountAggregator  implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        HashMap<Integer, AccountPayload> list;
        AccountPayload payload;
        AccountPayload payloadFromList;
        ObjectMapper mapper = new ObjectMapper();
        try {
            payload = mapper.readValue(newExchange.getIn().getBody().toString(), AccountPayload.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return newExchange;
        }


        if (oldExchange == null || oldExchange.getIn().getBody( ) == null) {
            list = new HashMap<>();
            list.put(payload.getAccountNo(), payload);
            newExchange.getIn().setBody(list);
            return newExchange;
        }

        list  = (HashMap<Integer, AccountPayload>)oldExchange.getIn().getBody();
        payloadFromList = list.get(payload.getAccountNo());
        if (payloadFromList == null) {
            list.put(payload.getAccountNo(), payload);
        } else {
            payloadFromList.setAmount(payloadFromList.getAmount()+payload.getAmount());
            //list.put(payloadFromList.getAccountNo(), payloadFromList);

        }
        newExchange.getIn().setBody(list);
        return newExchange;

    }
}
