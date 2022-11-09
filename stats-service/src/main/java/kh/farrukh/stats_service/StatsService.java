package kh.farrukh.stats_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.stats_service.payloads.StatsRequestDTO;
import kh.farrukh.stats_service.payloads.StatsResponseDTO;

import java.util.List;

public interface StatsService {

    PagingResponse<StatsResponseDTO> getStatsList(
            Long billId,
            int pageNumber,
            int pageSize
    );

    List<StatsResponseDTO> getAllStatsOfBill(long billId);

    StatsResponseDTO getStatsById(long id);

    StatsResponseDTO addStats(StatsRequestDTO statsDTO);

    StatsResponseDTO updateStats(long id, StatsRequestDTO statsDTO);

    void deleteStatsById(long id);

    void deleteStatsByBillId(long billId);

    void updateTotalPriceOfStatsByBillId(long billId, Double price);
}
