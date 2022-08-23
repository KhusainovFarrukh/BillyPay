package kh.farrukh.stats;

import kh.farrukh.common.paging.PagingResponse;

import java.util.List;

public interface StatsService {

    PagingResponse<Stats> getStatsList(
            Long billId,
            int pageNumber,
            int pageSize
    );

    List<Stats> getAllStatsOfBill(long billId);

    Stats getStatsById(long id);

    Stats addStats(StatsDTO statsDTO);

    Stats updateStats(long id, StatsDTO statsDTO);

    void deleteStatsById(long id);

    void deleteStatsByBillId(long billId);

    void updateTotalPriceOfStatsByBillId(long billId, Double price);
}
