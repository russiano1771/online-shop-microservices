package DKG.inventory_service.service;

import DKG.inventory_service.dto.InventoryResponse;
import DKG.inventory_service.model.Inventory;
import DKG.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

//    @SneakyThrows //  Аннотация @SneakyThrows позволяет бесшумно выбрасывать проверяемые исключения, не объявляя их явно в условии throws вашего метода
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
//        log.info("Wait start");
//        Thread.sleep(10000); // эмитируем медленное соединение при создании заказа,ордер сервис обратится к данному сервису инвентаризации и будет ждать ответа 10 секунда, но уже после 3 секунд должен сработать резервный вариант
//        log.info("Wait end");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
