package kh.farrukh.stats;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

    Page<Stats> findAllByBillId(long billId, Pageable pageable);

    void deleteAllByBillId(long billId);

    @Modifying
    @Transactional
    @Query("update Stats s set s.totalPrice = s.amount * ?2 where s.billId = ?1")
    void updateTotalPriceByBillId(long billId, Double price);
}
