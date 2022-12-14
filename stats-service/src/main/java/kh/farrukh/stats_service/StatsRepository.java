package kh.farrukh.stats_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

    List<Stats> findAllByBillId(long billId);

    Page<Stats> findAllByBillId(long billId, Pageable pageable);

    void deleteAllByBillId(long billId);

    @Modifying
    @Transactional
    @Query("update Stats stats set stats.totalPrice = stats.amount * :price where stats.billId = :billId")
    void updateTotalPriceByBillId(long billId, Double price);
}
