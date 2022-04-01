package org.krish.kafka.avro;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class KafkaAvroProducerClient {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Please provide command line arguments: configPath topic");
			System.exit(1);
		}

		final Properties props = loadConfig(args[0]);

		// Create topic if needed
		final String topic = args[1];
		final String key = "1";

		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

		Producer<String, Customer> producer = new KafkaProducer<String, Customer>(props);

		Customer record = Customer.newBuilder().setFirstName("John").setLastName("Doe").setAge(25)
				.setHeight(175f).setWeight(75f).setAutomatedEmail(false).build();

		producer.send(new ProducerRecord<String, Customer>(topic, key, record), new Callback() {

			@Override
			public void onCompletion(RecordMetadata m, Exception e) {
				if (e != null) {
					e.printStackTrace();
				} else {
					System.out.printf("Produced record to topic %s partition [%d] @ offset %d%n", m.topic(),
							m.partition(), m.offset());
				}
			}
		});

		producer.close();

	}

	public static Properties loadConfig(final String configFile) throws IOException {
		if (!Files.exists(Paths.get(configFile))) {
			throw new IOException(configFile + " not found.");
		}
		final Properties cfg = new Properties();
		try (InputStream inputStream = new FileInputStream(configFile)) {
			cfg.load(inputStream);
		}
		return cfg;
	}
}
