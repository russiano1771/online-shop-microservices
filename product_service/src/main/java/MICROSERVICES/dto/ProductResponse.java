package MICROSERVICES.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // для создания геттеров и сеттеров
@Builder // для создания метода builder, позволяет вызвать метод Product.builder() в продакт сервис // строим объект класса продукт с помощью данных полученных с клиента из productRequest
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
