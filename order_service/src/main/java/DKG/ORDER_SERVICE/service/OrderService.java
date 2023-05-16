package DKG.ORDER_SERVICE.service;

import DKG.ORDER_SERVICE.dto.InventoryResponse;
import DKG.ORDER_SERVICE.dto.OrderLineItemsDto;
import DKG.ORDER_SERVICE.dto.OrderRequest;
import DKG.ORDER_SERVICE.event.OrderPlacedEvent;
import DKG.ORDER_SERVICE.model.Order;
import DKG.ORDER_SERVICE.model.OrderLineItems;
import DKG.ORDER_SERVICE.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    // если заглянуть в реализацию класса kafkaTemplate, то там мы увидим что он принимает ключ и значение
    // ключ - String, значение - OrderPlacedEvent
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    // builder - строитель, метод позволяет пошагово строить сложные объекты
    // Паттерн Строитель предлагает вынести конструирование объекта за пределы его собственного класса, поручив это дело отдельным объектам, называемым строителями.(в ордер сервисе вызываем билдеров и строим объект)
    // webclient позволяет отправлять http запросы, с помощью которых будут общаться наши микросервисы
    public void createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumbers(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =
                orderRequest.getOrderLineItemsDto()
                        .stream()
                        .map(this::fromDtoOrderToJustOrder)
                        .toList();
        order.setOrderLineItems(orderLineItemsList);

        List<String> listOfSkuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList(); // вынимаем только код товара из сущности "содержимое заказа"
        // обращаемся к сервису инвенторизации и узнаем есть ли товар в наличии
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build() // вызываем метод билд, затем нам нужны так называемые билдеры (строители) что бы построить сложный объект, в данном случае объект запроса
                // синхронный вызов, поток блокируется пока не получим ответ
                .get() // отправляем гет запрос что бы узнать есть ли товар в наличии
                .uri("http://inventory-service/api/inventory", // передаем код товара через url в параметре
                        uriBuilder -> uriBuilder.queryParam("skuCode", listOfSkuCodes).build()) // передаем URL и параметр запроса в виде skuCode=iphone13&samsungs22 и тд и тп
               .retrieve() // retrieve() -- забрать класс типа Boolean
                .bodyToMono(InventoryResponse[].class)
                .block(); // Здесь поток блокируется, пока запрос не выполнится. В этом случае мы получаем запрашиваемую модель сразу же после завершения выполнения метода.


            boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        // нам вернулся с сервиса инвентаризации массив с объектами типа InventoryResponse, и теперь мы спрашиваем во всех ли объектах (.allMatch) переменная isInStock() == true
        if (allProductsInStock){
        orderRepository.save(order); // если на запрос о наличии товара пришел положительный ответ то создаем заказ.

            // если заглянуть в реализацию класса kafkaTemplate, то там мы увидим что он принимает ключ и значение
            // ключ - "Notification topic", значение - new OrderPlacedEvent(order.getOrderNumbers()
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumbers()));
        } else {
            throw new IllegalStateException("Товара нет в наличии.");
        }
    }




    public OrderLineItems fromDtoOrderToJustOrder(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
