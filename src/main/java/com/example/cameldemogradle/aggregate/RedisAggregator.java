package com.example.cameldemogradle.aggregate;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.HashMap;

public class RedisAggregator  implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        HashMap<Integer, AccountPayload> list;
        AccountPayload payload;
        AccountPayload dataPayload;
        AccountPayload payloadFromList;
        ObjectMapper mapper = new ObjectMapper();
        try {
            payload = mapper.readValue(newExchange.getIn().getBody().toString(), AccountPayload.class);
            //get value from Redis if it exists
            String dataValue = newExchange.getIn().getHeader("CamelRedis.Value", String.class);
            if (dataValue == null || !dataValue.contains("{")) { //get rid of buggy data that is not json
                dataPayload = null;
            } else {
                dataPayload = mapper.readValue(dataValue, AccountPayload.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return newExchange;
        }

        //
        if (oldExchange == null || oldExchange.getIn().getBody( ) == null) {
            list = new HashMap<>();
            payload = sumPayloads(dataPayload, payload);
            list.put(payload.getAccountNo(), payload);
            newExchange.getIn().setBody(list);
            return newExchange;
        }

        list  = (HashMap<Integer, AccountPayload>)oldExchange.getIn().getBody();
        payloadFromList = list.get(payload.getAccountNo());
        AccountPayload newPayload;
        if (payloadFromList == null) {
            newPayload = sumPayloads(dataPayload, payload);
            list.put(payload.getAccountNo(), newPayload);
        } else {
            payloadFromList.setAmount(payload.getAmount() + payloadFromList.getAmount());
            //list.put(payloadFromList.getAccountNo(), payloadFromList);

        }
        newExchange.getIn().setBody(list);
        return newExchange;

    }

    private AccountPayload sumPayloads(AccountPayload dataPayload, AccountPayload listPayload) {
        if (dataPayload == null && listPayload == null) {
            return null;
        }

        if (dataPayload == null) {
            return listPayload;
        }
        if (listPayload == null) {
            return dataPayload;
        }
        double amount = listPayload.getAmount();
        dataPayload.setAmount(dataPayload.getAmount() + amount);
        return dataPayload;



    }
}
