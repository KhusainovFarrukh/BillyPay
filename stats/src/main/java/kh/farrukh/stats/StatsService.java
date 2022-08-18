package kh.farrukh.stats;

import kh.farrukh.stats.utils.paging.PagingResponse;

public interface StatsService {

    PagingResponse<Stats> getStatsList(
//            Long billId,
            int pageNumber,
            int pageSize
    );

    Stats getStatsById(long id);

    Stats addStats(StatsDTO statsDTO);

    Stats updateStats(long id, StatsDTO statsDTO);

    void deleteStatsById(long id);
}
