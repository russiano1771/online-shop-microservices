package DKG.ORDER_SERVICE.controller;

import DKG.ORDER_SERVICE.dto.OrderRequest;
import DKG.ORDER_SERVICE.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")  // fallbackMethod это имя резервного метода
    // name - переменная которая хранит название тех свойств,к которым применяются параметры в application.properties на случай отключения связи между сервисами (например resilience4j.circuitbreaker.instances.inventory.failureRateThreshold=50)
    public String createOrder(@RequestBody OrderRequest orderRequest){
               orderService.createOrder(orderRequest);
       return "Заказ успешно создан.";

    }
    public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Упс! Что то пошло не так... Повторите попытку позже";
    }
}
