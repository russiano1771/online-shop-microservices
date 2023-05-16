package DKG.ORDER_SERVICE.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


/* Спецификация JPA гласит, что сущность – это обычный Java класс, который удовлетворяет следующим условиям:

        Должен быть помечен аннотацией @Entity.

        В классе должен быть конструктор без аргументов.

        Класс не должен быть закрытым (final).

        И обязательно должно быть поле (или несколько полей) ID с соответствующей аннотацией. */


@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @GeneratedValue(strategy = GenerationType.AUTO) // автоматически генерируются уникальные айди
    @Id
    private Long id;
    private String orderNumbers; // номер заказа
    @OneToMany(cascade = CascadeType.ALL) //cascade = CascadeType.ALL Если удаляю заказ то и содержимое заказа удаляется и тд и тп // Пример: Топик является родительской сущностью в том смысле, что комментарий сам по себе не существует, а всегда относится к топику. Его жизненный цикл привязан к топику. При удалении топика надо удалять из базы и его комментарии, а при сохранении топика — сохранять и его комментарии. Тут то и уместны каскадные операции — то есть те, которые распространяются и на дочернюю сущность.
    private List<OrderLineItems> orderLineItems; // содержимое заказа, какие товары содержатся в заказе
}
