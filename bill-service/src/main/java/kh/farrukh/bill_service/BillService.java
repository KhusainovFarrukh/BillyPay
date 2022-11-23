package kh.farrukh.bill_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.payloads.BillRequestDTO;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.StatsIdDTO;

public interface BillService {

    PagingResponse<BillResponseDTO> getBills(
            String token,
            Long ownerId,
            int pageNumber,
            int pageSize
    );

    PagingResponse<BillWithStatsResponseDTO> getBillsWithStats(
            String token,
            Long ownerId,
            int pageNumber,
            int pageSize
    );

    BillResponseDTO getBillById(long id);

    BillResponseDTO addBill(String token, BillRequestDTO billRequestDTO);

    BillResponseDTO updateBill(long id, BillRequestDTO billRequestDTO);

    void deleteBillById(String token, long id);

    BillResponseDTO addStatsToBill(long id, StatsIdDTO statsIdDTO);

    BillResponseDTO deleteStatsFromBill(long id, StatsIdDTO statsIdDTO);
}
