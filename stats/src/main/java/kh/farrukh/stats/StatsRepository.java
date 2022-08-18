package kh.farrukh.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

//    Page<Stats> findAllByBill_Id(long billId, Pageable pageable);
}
