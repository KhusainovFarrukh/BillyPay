package kh.farrukh.bill;

import kh.farrukh.bill.utils.paging.PagingResponse;

public interface BillService {

    PagingResponse<Bill> getBills(
//            Long ownerId,
            int pageNumber,
            int pageSize
    );

    Bill getBillById(long id);

    Bill addBill(BillDTO billDTO);

    Bill updateBill(long id, BillDTO billDTO);

    void deleteBillById(long id);

    Bill addStatsToBill(long id, StatsIdDTO statsIdDTO);

    Bill deleteStatsFromBill(long id, StatsIdDTO statsIdDTO);
}
