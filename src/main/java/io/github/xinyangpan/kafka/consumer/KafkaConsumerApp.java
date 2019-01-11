package io.github.xinyangpan.kafka.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.ConsumerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import io.github.xinyangpan.kafka.vo.KfMsg;

@SpringBootApplication
public class KafkaConsumerApp {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ConfigurableApplicationContext context = SpringApplication.run(KafkaConsumerApp.class, args);

		ConsumerFactory<String, String> consumerFactory = context.getBean(ConsumerFactory.class);
		Consumer<String, String> createConsumer = consumerFactory.createConsumer("test-consumer-group1", "abc");
		// subscribe
				createConsumer.subscribe(Lists.newArrayList("testp"));
		// assign
//		createConsumer.assign(Lists.newArrayList(new TopicPartition("testp", 0)));
//		createConsumer.seek(new TopicPartition("testp", 0), 0);

		while (true) {
			ConsumerRecords<String, String> consumerRecords = createConsumer.poll(Duration.ofSeconds(5));
			if (!consumerRecords.isEmpty()) {
				Iterable<ConsumerRecord<String, String>> records = consumerRecords.records("testp");
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record);
					KfMsg kfMsg = objectMapper.readValue(record.value(), KfMsg.class);
					System.out.println(kfMsg);
					System.out.println(System.currentTimeMillis() - kfMsg.getTime());
				}
			} else {
				Thread.sleep(10);
			}
		}

	}

}
