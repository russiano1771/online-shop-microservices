package MICROSERVICES.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product") // аналог entity но для mongodb, означает что этот класс будет сущностью
@AllArgsConstructor
@NoArgsConstructor
@Builder // позволяет строить шаблоны
@Data // Что сделает аннотация  @Data? Она на этапе компиляции сгенерирует геттеры\сеттеры для всех полей, toString и переопределит equals и hashCode по стандартам.
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price; // Класс BigDecimal предоставляет операции для арифметики, управления масштабом, округления, сравнения, хеширования и преобразования формата
}
