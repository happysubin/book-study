package study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootApplication
public class KafkaApplication {

	private final static String TOPIC_NAME = "test";
	private final static String BOOTSTRAP_SERVERS = "http://localhost:29092";

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("test", 1, (short) 1);

	}

	@PostConstruct
	public void runner() throws ExecutionException, InterruptedException {

//		kafkaTemplate().send("test", "Hello, Kafka!");

		Properties configs = new Properties();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		String messageValue = "testMessage";
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
		RecordMetadata recordMetadata = producer.send(record).get();
		log.info("ㅙㅙㅙㅙ, {}", recordMetadata.toString());
		System.out.println("recordMetadata = " + recordMetadata); // test-2@1 2번 파티션에 적재되었으며 헤당 레코드에 부여된 오프셋 번호는 1이라는 뜻.
		producer.flush();
		producer.close();

	}

	@KafkaListener(id = "myId", topics = "test")
	public void listen(String in) {
		System.out.println(in);
	}
}
