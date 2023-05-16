package DKG.ORDER_SERVICE.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


        //  @LoadBalanced  данная аннотация позволят распределять нагрузку между серверами inventory_service,
        // когда один экзмпляр сервиса слишком нагружен или просто выключен, он обращается к другому и тем самым балансирует нагрузку
        @Bean
        @LoadBalanced // позволяет балансировать нагрузку между экземплярами микросервиса, например инвентори сервис
        public WebClient.Builder webClientBuilder(){
            return WebClient.builder(); // webclient позволяет отправлять http запросы, с помощью которых будут общаться наши микросервисы
            // builder - строитель, метод позволяет пошагово строить сложные объекты
            // Паттерн Строитель предлагает вынести конструирование объекта за пределы его собственного класса, поручив это дело отдельным объектам, называемым строителями.(в ордер сервисе вызываем билдеров и строим объект)
        }
}
