package io.github.xinyangpan.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xinyangpan.kafka.consumer.KafkaConsumerApp;
import io.github.xinyangpan.kafka.vo.KfMsg;

@SpringBootApplication
public class KafkaProducerApp {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ConfigurableApplicationContext context = SpringApplication.run(KafkaConsumerApp.class, args);
		KafkaTemplate<String, String> kafkaTemplate = context.getBean(KafkaTemplate.class);
		System.out.println(kafkaTemplate.isTransactional());
		for (int i = 0; i < 100; i++) {
			String msg = objectMapper.writeValueAsString(new KfMsg("Hello Sean"));
			ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("testp", msg);
			listenableFuture.addCallback(result -> System.out.println(result), ex -> ex.printStackTrace());
			Thread.sleep(5);
		}
	}

}
