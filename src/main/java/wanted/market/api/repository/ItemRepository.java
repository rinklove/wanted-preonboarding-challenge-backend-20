package wanted.market.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
