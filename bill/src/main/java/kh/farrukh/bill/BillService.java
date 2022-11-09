package kh.farrukh.bill;

import kh.farrukh.bill.payloads.BillRequestDTO;
import kh.farrukh.bill.payloads.BillResponseDTO;
import kh.farrukh.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.clients.bill.StatsIdDTO;
import kh.farrukh.common.paging.PagingResponse;

public interface BillService {

    PagingResponse<BillResponseDTO> getBills(
            Long ownerId,
            int pageNumber,
            int pageSize
    );

    PagingResponse<BillWithStatsResponseDTO> getBillsWithStats(
            Long ownerId,
            int pageNumber,
            int pageSize
    );

    BillResponseDTO getBillById(long id);

    BillResponseDTO addBill(BillRequestDTO billDTO);

    BillResponseDTO updateBill(long id, BillRequestDTO billDTO);

    void deleteBillById(long id);

    BillResponseDTO addStatsToBill(long id, StatsIdDTO statsIdDTO);

    BillResponseDTO deleteStatsFromBill(long id, StatsIdDTO statsIdDTO);
}
