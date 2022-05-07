# kafka-avro-producer
Kafka Producer that uses Apache Avro as the messaging protocol to exchange messages 

This is simple java application acts a producer for a Kafka Topic. It uses Apache Avro as the messaging protocol. 
The porject generates the Avro classes and utilizes the Specific Record class to read the generated classes to build the message.

Below is the sample avro object used in the application.
```
{
     "type": "record",
     "namespace": "org.krish.kafka.avro",
     "name": "Customer",
     "version": "1",
     "fields": [
       { "name": "first_name", "type": "string", "doc": "First Name of Customer" },
       { "name": "last_name", "type": "string", "doc": "Last Name of Customer" },
       { "name": "age", "type": "int", "doc": "Age at the time of registration" },
       { "name": "height", "type": "float", "doc": "Height at the time of registration in cm" },
       { "name": "weight", "type": "float", "doc": "Weight at the time of registration in kg" },
       { "name": "automated_email", "type": "boolean", "default": true, "doc": "Field indicating if the user is enrolled in marketing emails" }
     ]
}
```
