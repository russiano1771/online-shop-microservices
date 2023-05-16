package DKG.ORDER_SERVICE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {

    private Long id;
    private String SkuCode; //  SKU (англ. Stock keeping unit) — это код, состоящий из цифр и букв, который присваивают товарам для отслеживания остатков на складе,
    private BigDecimal price;
    private Integer quantity; // количество товаров в заказе;
}
