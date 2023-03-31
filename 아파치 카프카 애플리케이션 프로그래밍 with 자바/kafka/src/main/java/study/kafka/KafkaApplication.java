package study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}


	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		return new KafkaAdmin(configs);
	}


	public void runner() {

		kafkaTemplate().send("test", "Hello, Kafka!");

//		Properties configs = new Properties();
//		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
//		String messageValue = "testMessage";
//		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
//		producer.send(record);
//		log.info("ㅙㅙㅙㅙ, {}", record);
//		producer.flush();
//		producer.close();

	}
}
