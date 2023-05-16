package DKG.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j // Позволяет получать инфу в логи
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic") // с помощью данной аннотации прослушиваем события в указанной теме topics = "notificationTopic"
	public void handleNotification(OrderPlacedEvent orderPlacedEvent){

		// какая то бизнес логика... (НАПРИМЕР ОТПРАВКА СООБЩЕНИЯ НА ИМЕЙЛ)

		log.info("Поступил новый заказ под номером: {}", orderPlacedEvent.getOrderNumber());
	}

}
