package DKG.ORDER_SERVICE.repository;

import DKG.ORDER_SERVICE.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
