package DKG.ORDER_SERVICE.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/* Спецификация JPA гласит, что сущность – это обычный Java класс, который удовлетворяет следующим условиям:

        Должен быть помечен аннотацией @Entity.

        В классе должен быть конструктор без аргументов.

        Класс не должен быть закрытым (final).

        И обязательно должно быть поле (или несколько полей) ID с соответствующей аннотацией. */
@Entity
@Table(name = "t_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - используется встроенный в БД тип данных столбца -identity - для генерации значения первичного ключа.
    @Id
    private Long id;
    private String SkuCode; //  SKU (англ. Stock keeping unit) — это код, состоящий из цифр и букв, который присваивают товарам для отслеживания остатков на складе,
    private BigDecimal price;
    private Integer quantity; // количество товаров в заказе
}
