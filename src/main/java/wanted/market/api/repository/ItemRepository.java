package wanted.market.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i.member.nickname from Item i where i.no = :no")
    String findMember_NicknameByNo(Long no);
}
