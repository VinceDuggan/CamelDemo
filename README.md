Project requires Redis and Kafka running

Project generates test Account Invoices
AccountNo
AccountName
Amount

Pops them onto a kafka topic  (topicname=event1)

A 2nd route reads  the topic and filters out low account numbers (arbitrary rule)
and pops them onto a new topic (topicname=event2)

A 3rd route reads the new topic and aggregates account totals, using Redis for persistance

A REST GET endpoint http://localhost:8080/camel/api/accounts/{accountno} will read Redis
and retrieve the account details for display
