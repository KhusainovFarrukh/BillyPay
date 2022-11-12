package kh.farrukh.stats_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.stats.payloads.StatsRequestDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;

import java.util.List;

public interface StatsService {

    PagingResponse<StatsResponseDTO> getStatsList(
            Long billId,
            int pageNumber,
            int pageSize
    );

    List<StatsResponseDTO> getAllStatsOfBill(long billId);

    StatsResponseDTO getStatsById(long id);

    StatsResponseDTO addStats(StatsRequestDTO statsRequestDTO);

    StatsResponseDTO updateStats(long id, StatsRequestDTO statsRequestDTO);

    void deleteStatsById(long id);

    void deleteStatsByBillId(long billId);

    void updateTotalPriceOfStatsByBillId(long billId, Double price);
}
