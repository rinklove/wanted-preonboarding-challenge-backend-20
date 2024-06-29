package wanted.market.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
