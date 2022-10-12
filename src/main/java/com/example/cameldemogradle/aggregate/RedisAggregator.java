package com.example.cameldemogradle.aggregate;

import com.example.cameldemogradle.AccountPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.HashMap;

/**
 * Reads account data from Redis and adds new 'invoices' to total, using a local list
 */

public class RedisAggregator  implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        HashMap<Integer, AccountPayload> list;
        AccountPayload payload;
        AccountPayload dataPayload;
        AccountPayload payloadFromList;
        ObjectMapper mapper = new ObjectMapper();
        try {
            //New invoice
            payload = mapper.readValue(newExchange.getIn().getBody().toString(), AccountPayload.class);

            //get value from Redis if it exists
            String dataValue = newExchange.getIn().getHeader("CamelRedis.Value", String.class);

            if (dataValue == null || !dataValue.contains("{")) { //get rid of buggy test data that is not json
                dataPayload = null;
            } else {
                dataPayload = mapper.readValue(dataValue, AccountPayload.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return newExchange;
        }

        //If first time, use either the value from Redis + new invoice, or just new invoice if redis returned nothing
        if (oldExchange == null || oldExchange.getIn().getBody( ) == null) {
            list = new HashMap<>();

            //Add the 2 together
            payload = sumPayloads(dataPayload, payload);
            list.put(payload.getAccountNo(), payload);
            newExchange.getIn().setBody(list);
            return newExchange;
        }

        //The internal list trumps the redis value, if the account is in the internal list
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

    /**
     * Add two AccountPayloads together, checking for nulls
     * @param dataPayload -
     * @param listPayload -
     * @return
     */
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
